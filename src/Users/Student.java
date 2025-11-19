package Users;

import Courses.Quiz;
import java.util.*;
import JSON.StudentService;

public class Student extends User {
    private List<String> enrolledCourses;              
    private Map<String, List<String>> progress;         
    private Map<String, Integer> quizScores;
    public static final int PASSING_SCORE = 50; 

  
    private transient StudentService studentService;
    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, "student", username, email, passwordHash);
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
        this.quizScores = new HashMap<>();

    }
    public void setStudentService(StudentService service) {
        this.studentService = service;
    }
    public List<Courses.Course> browseCourses() {
        return studentService.browseCourses();
    }
    public boolean enrollCourse(String courseId) {
        return studentService.enrollStudentInCourse(this, courseId);
    }
    public List<Courses.Course> viewEnrolledCourses() {
        return studentService.getEnrolledCourses(this);
    }
    public List<Courses.Lesson> viewLessons(String courseId) {
        return studentService.getLessonsForCourse(courseId);
    }
    public boolean markLessonCompleted(String courseId, String lessonId) {
        return studentService.markLessonCompleted(this, courseId, lessonId);
    }
//class Quiz
    public int takeQuiz(String lessonId, Quiz quiz, List<Integer> answers) {
    return studentService.submitQuiz(this, lessonId, quiz, answers);
}

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
    public Map<String, List<String>> getProgress() {
        return progress;
    }
    public Map<String, Integer> getQuizScores() {
    return quizScores;
}

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setProgress(Map<String, List<String>> progress) {
        this.progress = progress;
    }

    public void setQuizScores(Map<String, Integer> quizScores) {
        this.quizScores = quizScores;
    }

    

}

