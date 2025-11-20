package JSON;

import Users.Student;
import Courses.Course;
import Courses.Lesson;
import Courses.Quiz;
import Users.User;
import java.io.IOException;

import java.util.*;

public class StudentService extends JsonDatabaseManager<Student> {

    private UserService userService;
    private CourseService courseService;

   public StudentService(UserService userService, CourseService courseService, String filePath) throws IOException {
        super(filePath, Student.class); 
        this.userService = userService;
        this.courseService = courseService;
    }
    public List<Course> browseCourses() {
        return courseService.getDb();   
    }

    public boolean enrollStudentInCourse(Student student, String courseId) {
        if (student.getEnrolledCourses().contains(courseId)) {
            return false;
        }
        student.getEnrolledCourses().add(courseId);
        save(); 
        courseService.save();
        return true;
    }

    public List<Course> getEnrolledCourses(Student student) {
        List<Course> result = new ArrayList<>();

        for (String cid : student.getEnrolledCourses()) {
            Course c = courseService.getCourseById(cid);
            if (c != null) result.add(c);
        }
        return result;
    }

    public List<Lesson> getLessonsForCourse(String courseId) {
        Course c = courseService.getCourseById(courseId);
        if (c == null) return new ArrayList<>();
         return c.getLessons();  
    }
   public boolean markLessonCompleted(Student student, String courseId, String lessonId) {
    // Check the student score
    Integer score = student.getQuizScores().get(lessonId);
    if (score == null || score <Student.PASSING_SCORE ) {
        return false;
    }
    student.getProgress().putIfAbsent(courseId, new ArrayList<>());
    if (!student.getProgress().get(courseId).contains(lessonId)) {
        student.getProgress().get(courseId).add(lessonId);
          save();// save students.json  
        return true;
    }
    return false;
}

    
    public int submitQuiz(Student student, String lessonId, Quiz quiz, List<Integer> studentAnswers) {

    if (student.getQuizScores().containsKey(lessonId)) {
        return -1;  // quiz already taken
    }
    int score = 0;
    //method get correct answers
    for (int i = 0; i < quiz.getCorrectAnswers().size(); i++) {
        if (studentAnswers.get(i) == quiz.getCorrectAnswers().get(i)) {
            score++;
        }
    }

    student.getQuizScores().put(lessonId, score);
    save();

    return score; 
}

    public Student getStudentById(String studentId) {
    for(Student s : db) {
        if(s.getUserID().equals(studentId)) {
            return s;
        }
    }
    return null;
}

     public Map<String, List<String>> getStudentProgress(Student s) {
        return s.getProgress();
    }
     //Y check when retrieving lessons in the course, in y CourseService or LessonService:
     
     
   public boolean addStudent(Student student) {
        if (db.stream().anyMatch(s -> s.getUserID().equals(student.getUserID()))) return false;
        db.add(student);
        save();  //save students.json

        // Also save to users.json
        User user = new User(student.getUserID(), student.getPassword(), student.getUserName(), "Student", student.getEmail());
    userService.addUser(user); 
    userService.save(); 
    return true;
    }

}