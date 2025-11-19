package JSON;

import Courses.Course;
import Courses.Lesson;
import java.io.IOException;
import java.util.ArrayList;

public class CourseService extends JsonDatabaseManager<Course> {
    
    public CourseService(String fileName) throws IOException {
        super(fileName, Course.class);
    }
    
    public boolean createCourse(Course course)
    {
        if(getCourseById(course.getCourseId())!=null)
        {
            return false;
        }
        db.add(course);
        save();
        return true;
    }
    public Course getCourseById(String courseId)
    {
        for(Course c : db)
        {
            if(c.getCourseId().equals(courseId))
            {
                return c;
            }
        }
        return null;
    }
    public boolean updateCourse(Course updatedCourse){
        for(int i=0;i<db.size();i++)
        {
            if(db.get(i).getCourseId().equals(updatedCourse.getCourseId()))
            {
                db.set(i,updatedCourse);
                save();
                return true;
            }
        }
        return false;
    }
    public boolean deleteCourse(String courseId)
    {
        for(int i=0;i<db.size();i++)
        {
            if(db.get(i).getCourseId().equals(courseId))
            {
                db.remove(i);
                save();
                return true;
            }
        }
        return false;
    }
     public ArrayList<Course> getCoursesByInstructor(String instructorId) {
        ArrayList<Course> result = new ArrayList<>();
        for(Course c : db) {
            if(c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }
     
    public ArrayList<Course> getAllCourses() {
        return db;
    }
    
    public boolean enrollStudent(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        if(course != null && !courseHasStudent(courseId, studentId )) {
            course.enrollStudent(studentId);
            save();
            return true;
        }
        return false;
    }
    public boolean courseHasStudent(String courseId, String studentId) {
        Course course = getCourseById(courseId);
        if(course.isStudentEnrolled(studentId)) {
            return true;
        }
        return false;
    }
    
    public ArrayList<Course> getEnrolledCourses(String studentId) {
        ArrayList<Course> result = new ArrayList<>();
        for(Course c : db) {
            if(c.isStudentEnrolled(studentId)) {
                result.add(c);
            }
        }
        return result;
    }
    public ArrayList<Course> getInstructorCourses(String instructorId) {
        ArrayList<Course> result = new ArrayList<>();
        for(Course c : db) {
            if(c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }
    public boolean addLesson(String courseId, Lesson lesson) {
        Course course = getCourseById(courseId);
        if(course != null) {
            course.addLesson(lesson);
            save();
            return true;
        }
        return false;
    }
    
    public boolean updateLesson(String courseId, String lessonId, Lesson updatedLesson) {
        Course course = getCourseById(courseId);
        if(course != null) {
            course.updateLesson(lessonId, updatedLesson);
            save();
            return true;
        }
        return false;
    }
    
    public boolean deleteLesson(String courseId, String lessonId) {
        Course course = getCourseById(courseId);
        if(course != null) {
            course.removeLesson(lessonId);
            save();
            return true;
        }
        return false;
    }
    
    public ArrayList<Lesson> getLessons(String courseId) {
        Course course = getCourseById(courseId);
        if(course==null)
        {
             return new ArrayList<>(); 
        }
        return course.getLessons();
    }
    
    public ArrayList<String> getEnrolledStudents(String courseId) {
        Course course = getCourseById(courseId);
        if(course==null)
        {
            return new ArrayList<>();
        }
        return course.getStudents();
    }
}
