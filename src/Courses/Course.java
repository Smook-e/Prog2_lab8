package Courses;

import java.util.ArrayList;

public class Course {
    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private ArrayList <Lesson> lessons;
    private ArrayList <String> students;
    private String status;
    public Course(){}

    public Course(String courseId, String title, String description, String instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.students = new ArrayList<>();
        this.status = "pending";
    }

    public String getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public ArrayList<String> getStudents() {
        return students;
    }
    public String getStatus() {
        return this.status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void addLesson(Lesson l)
    {
        lessons.add(l);
    }
    public Lesson getLessonById(String lessonId)
    {
        for(Lesson l : lessons)
        {
            if(l.getLessonId().equals(lessonId))
            {
                return l;
            }
        }
        return null;
    }
    
    public void removeLesson(String lessonId)
    {
        if(getLessonById(lessonId)!=null)
        {
            lessons.remove(getLessonById(lessonId));
        }
    }
    public boolean updateLesson(String lessonId, Lesson updatedLesson) {
        for(int i = 0; i < lessons.size(); i++) {
            if(lessons.get(i).getLessonId().equals(lessonId)) {
                lessons.set(i, updatedLesson);
                return true;
            }
        }
        return false;
    }
    public void enrollStudent(String studentId) {
        if(!students.contains(studentId)) {
            students.add(studentId);
        }
    }
    
    public void unenrollStudent(String studentId) {
        students.remove(studentId);
    }
    
    public boolean isStudentEnrolled(String studentId) {
        return students.contains(studentId);
    }
    
    public int getEnrolledStudentsCount() {
        return students.size();
    }
    public int getLessonsCount() {
        return lessons.size();
    }


}
