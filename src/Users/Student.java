package Users;

import java.util.*;
import JSON.StudentService;

public class Student extends User {
    private List<String> enrolledCourses;              
    private Map<String, List<String>> progress;         

    // If save the student to JSON, this field will be ignored.
    private transient StudentService studentService;
    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, "student", username, email, passwordHash);
        this.enrolledCourses = new ArrayList<>();
        this.progress = new HashMap<>();
    }
    public void setStudentService(StudentService service) {
        this.studentService = service;
    }//After loading a student from JSON, the studentService field is null.
    //Student student = objectMapper.readValue(jsonString, Student.class);
//At this point, student.getStudentService() is null because it was transient.
//If the student wants to do actions (like enroll in a course), they need the service.


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

    
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
    public Map<String, List<String>> getProgress() {
        return progress;
    }
}

