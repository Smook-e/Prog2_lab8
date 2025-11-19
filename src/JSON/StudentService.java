package JSON;

import Users.Student;
import Courses.Course;
import Courses.Lesson;

import java.util.*;

public class StudentService {

    private UserService userService;
    private CourseService courseService;

    public StudentService(UserService userService, CourseService courseService) {
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
        userService.save(); 
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
        student.getProgress().putIfAbsent(courseId, new ArrayList<>());

        if (!student.getProgress().get(courseId).contains(lessonId)) {
            student.getProgress().get(courseId).add(lessonId);
            userService.save();  
            return true;
        }

        return false;
    }
    public Student getStudentById(String studentId)
    {
        return (Student) userService.getUserByID(studentId);
    }
}
