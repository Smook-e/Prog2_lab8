package JSON;

import Courses.Certificate;
import Users.Student;
import Courses.Course;
import Courses.Lesson;
import Courses.Quiz;
import Users.User;
import java.io.IOException;

import java.util.*;
import javax.swing.JOptionPane;

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
            if (c != null) {
                result.add(c);
            }
        }
        return result;
    }

    public List<Lesson> getLessonsForCourse(String courseId) {
        Course c = courseService.getCourseById(courseId);
        if (c == null) {
            return new ArrayList<>();
        }
        return c.getLessons();
    }

    public Boolean markLessonCompleted(Student student, String courseId, String lessonId) {
        Integer score = student.getQuizScores().get(lessonId);
        if (score == null) {
            JOptionPane.showMessageDialog(null, "You must take the quiz first!");
            return null;
        }
        if (score < Student.PASSING_SCORE) {
            JOptionPane.showMessageDialog(null, "Quiz not passed yet!");
            return null;
        }

        student.getProgress().putIfAbsent(courseId, new ArrayList<>());

        if (!student.getProgress().get(courseId).contains(lessonId)) {
            student.getProgress().get(courseId).add(lessonId);
            save();
            return true;
        }
        return false;
    }

    public int submitQuiz(Student student, String lessonId, QuizService quizService, List<Integer> studentAnswers) {
        if (student.getQuizScores().containsKey(lessonId)) {
            return -1;
        }
        Quiz quiz = quizService.getQuizById(lessonId);
        if (quiz == null) {
            return -2;
        }
        int score = quizService.calculateScore(lessonId, studentAnswers);
        student.getQuizScores().put(lessonId, score);
        save();
        return score;
    }

    public Student getStudentById(String studentId) {
        for (Student s : db) {
            if (s.getUserID().equals(studentId)) {
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
        if (db.stream().anyMatch(s -> s.getUserID().equals(student.getUserID()))) {
            return false;
        }
        db.add(student);
        save();  //save students.json

        // Also save to users.json
        User user = new User(student.getUserID(), student.getPassword(), student.getUserName(), "Student", student.getEmail());
        userService.addUser(user);
        userService.save();
        return true;
    }

    public boolean hasCertificate(String studentId, String courseId) {
        Student student = getStudentById(studentId);
        if (student == null) {
            return false;
        }
        ArrayList<Certificate> certificates = student.getCertificates();
        if (certificates == null || certificates.isEmpty()) {
            return false;
        }
        for (Certificate cert : certificates) {
            if (cert.getCourseId().equals(courseId)) {
                return true;
            }
        }
        return false;
    }

}
