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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author HP
 */
public class InstructorManagment {
    private CourseService courseService;
    private StudentService studentService;
    public InstructorManagment(CourseService courseService,StudentService studentService)
    {
        this.courseService=courseService;
        this.studentService=studentService;
    }
    
    public boolean createCourse(Instructor instructor,String courseId,String title,String description)
    {
      
        Course course = new Course(courseId,title,description,instructor.getUserID());
        boolean done=courseService.createCourse(course);
        if(done)
        {
            instructor.addCreatedCourse(courseId);
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

    int total = 0;
    int count = 0;

    for (String sid : students) {
        Student s = studentService.getStudentById(sid);
        Integer score = s.getQuizScores().get(lessonId);

        if (score != null) {
            total += score;
            count++;
        }
    }

    return count == 0 ? 0 : (double) total / count;
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


   }

    

