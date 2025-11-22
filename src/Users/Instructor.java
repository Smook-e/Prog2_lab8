package Users;

import Courses.Course;
import JSON.InstructorManagment;
import java.util.*;


public class Instructor extends User {
    private List<String> createdCourses;
    private transient InstructorManagment instructorManagment;
    
    public Instructor(String userID,String password,String userName,String email)
    {
        super( userID,  password,  userName,"Instructor", email);
        this.createdCourses=new ArrayList<>();
    }
    public void setInstructorManagment(InstructorManagment instructorManagment)
    {
        this.instructorManagment=instructorManagment;
    }
    public List<String> getCreatedCourses()
    {
        return createdCourses;
    }
    public void addCreatedCourse(String courseId)
    {
        createdCourses.add(courseId);
    }
    public void removeCourse(String courseId)
    {
        createdCourses.remove(courseId);
    }
    
    public ArrayList<Course> getCourses() {
        if (instructorManagment == null) return new ArrayList<>();
        return instructorManagment.getCoursesByInstructor(this.getUserID());
    }

    public double getStudentCompletionPercentage(String studentId, String courseId) {
        if (instructorManagment == null) return 0;
        return instructorManagment.getStudentCourseCompletionPercentage(studentId, courseId);
    }

    public int getStudentQuizScore(String studentId, String lessonId) {
        if (instructorManagment == null) return 0;
        Map<String, Integer> scores = instructorManagment.getStudentQuizScores(studentId);
        return scores.getOrDefault(lessonId, 0);
    }
}
