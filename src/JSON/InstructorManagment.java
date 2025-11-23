/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

import Courses.Course;
import JSON.StudentService;
import Courses.Lesson;
import Users.Instructor;
import Users.Student;
import Users.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class InstructorManagment extends JsonDatabaseManager<Instructor> {
    private CourseService courseService;
    private StudentService studentService;
   public InstructorManagment(CourseService courseService, StudentService studentService, String filePath) throws IOException {
        super(filePath, Instructor.class); // <-- load instructors.json
        this.courseService = courseService;
        this.studentService = studentService;
    }
    
    public boolean createCourse(Instructor instructor,String courseId,String title,String description)
    {
      
        Course course = new Course(courseId,title,description,instructor.getUserID());
        boolean done=courseService.createCourse(course);
        if(done)
        {
            instructor.addCreatedCourse(courseId);
            save(); 
        } 
        return done;
       
    }
    public boolean updateCourse(Instructor instructor,Course course)
    {
        if(!course.getInstructorId().equals(instructor.getUserID()))
        {
            return false;
        }
        return courseService.updateCourse(course);
    }
    public boolean deleteCourse(Instructor instructor,String courseId)
    {
        if(!getCoursesByInstructor(instructor.getUserID()).contains(courseService.getCourseById(courseId)))
        {
            return false;
        }
        return courseService.deleteCourse(courseId);
    }
    public boolean createLesson(Instructor instructor,String courseId,String lessonId,String title,String content)
    {
        if(!getCoursesByInstructor(instructor.getUserID()).contains(courseService.getCourseById(courseId)))
        {
            return false;
        }
        Lesson lesson=new Lesson(lessonId,title,content);
        return courseService.addLesson(courseId, lesson);
    }
    public boolean editLesson(Instructor instructor,String courseId,Lesson lesson,String lessonId)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.updateLesson(courseId,lessonId,lesson);
    }
    public boolean deleteLesson(Instructor instructor,String courseId,String lessonId)
    {
        if(!instructor.getCreatedCourses().contains(courseId))
        {
            return false;
        }
        return courseService.deleteLesson(courseId, lessonId);
    }
    
    
    public List<String> getStudentsInCourse(String courseId) {
        return courseService.getEnrolledStudents(courseId); 
    }
    
    public Map<String, List<String>> getStudentProgress(String studentId)
    {
        Student s = studentService.getStudentById(studentId);
        if (s == null) return null;

        return s.getProgress(); 
    }
    
    
    
    public CourseService getCourseService()
    {
        return courseService;
    }
    public StudentService getStudentService()
    {
        return studentService;
    }
    
    public void setCourseService(CourseService courseService)
    {
        this.courseService=courseService;
    }
    public void setStudentService(StudentService studentService)
    {
        this.studentService=studentService;
    }
    public ArrayList<Course> getCoursesByInstructor(String instructorId){
        return courseService.getCoursesByInstructor(instructorId);
    }
    
    public Map<String, Integer> getStudentQuizScores(String studentId) {
    Student s = studentService.getStudentById(studentId);
    return s.getQuizScores();  
}
public double getLessonAverage(String courseId, String lessonId) {
    ArrayList<String> students = courseService.getEnrolledStudents(courseId);

    Lesson lesson = courseService.getLessonById(courseId, lessonId);
    if (lesson == null || lesson.getQuiz() == null) return 0.0;

    String quizId = lesson.getQuiz().getQuizId();
    int total = 0;
    int count = 0;

    for (String sid : students) {
        Student s = studentService.getStudentById(sid);
        if (s == null) continue;

        Integer score = s.getQuizScores().get(quizId);
        if (score != null) {
            total += score;
            count++;
        }
    }

    if (count == 0) return 0.0;
    return (double) total / count;
}


public double getStudentAverageQuizScore(String studentId, String courseId) {
    Student student = studentService.getStudentById(studentId);
    Course course = courseService.getCourseById(courseId);

    if (student == null || course == null) return 0.0;

    Map<String, Integer> quizScores = student.getQuizScores();
    if (quizScores == null || quizScores.isEmpty()) return 0.0;

    int total = 0;
    int count = 0;

    for (Lesson lesson : course.getLessons()) {
        String lessonId = lesson.getLessonId();
        if (quizScores.containsKey(lessonId)) {
            total += quizScores.get(lessonId);
            count++;
        }
    }

    if (count == 0) return 0.0;
    return total * 1.0 / count;
}

   public double getStudentCourseCompletionPercentage(String studentId, String courseId) {
    Student student = studentService.getStudentById(studentId);
    Course course = courseService.getCourseById(courseId);

    if (student == null || course == null) return 0.0;
    int totalLessons = course.getLessons().size();
    if (totalLessons == 0) return 0.0;
    List<String> completedLessons = student.getProgress().getOrDefault(courseId, new ArrayList<>());
    //.getOrDefault(key, defaultValue) is a Java Map method that:
     //Tries to get the value for the key (courseId)
//If the key is not present (the student has not completed any lessons in this course yet), 
//it returns the default value, which is a new empty list.
    int completedCount = completedLessons.size();
    return (completedCount * 100.0) / totalLessons;
}
   
   public int getStudentCompletedLessonsCount(String studentId, String courseId) {
    Student student = studentService.getStudentById(studentId);
    Course course = courseService.getCourseById(courseId);

    if (student == null || course == null) return 0;
    return student.getProgress().getOrDefault(courseId, new ArrayList<>()).size();
}
  public boolean isCourseFullyCompleted(String studentId, String courseId) {
    return getStudentCourseCompletionPercentage(studentId, courseId) == 100.0;
}  
   
   
public boolean addInstructor(Instructor instructor, UserService users) {
        if (db.stream().anyMatch(i -> i.getUserID().equals(instructor.getUserID()))) return false;
        db.add(instructor);
        save();             // save instructors.json
    // Save users.json
    User user = new User(instructor.getUserID(), instructor.getPassword(), instructor.getUserName(), "Instructor", instructor.getEmail());
    users.addUser(user);  
    users.save();  
    return true;
    }


   }

    

