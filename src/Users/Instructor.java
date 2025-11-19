package Users;

import JSON.InstructorManagment;
import java.util.*;

public class Instructor extends User {
    private List<String> createdCourses;
    private transient InstructorManagment instructorManagment;
    public Instructor(String userID,String password,String userName,String email)
    {
        super( userID,  password,  userName, "Instructor", email);
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
}
