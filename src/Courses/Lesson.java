package Courses;

import java.util.ArrayList;

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private ArrayList<String> Resources;


    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.Resources = new ArrayList<>();
    }
    
    public String getLessonId() {
        return lessonId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getOptionalResources() {
        return Resources;
    }

    public void addResources(String Resource) {
        Resources.add(Resource);
    }
    
    public void removeResources(String Resource) {
        Resources.remove(Resource);
    }
    @Override
    public String toString() {
        return "Lesson{" +
                "lessonId=" + lessonId +
                ", title=" + title +
                ", content=" + content +
                ", resources=" + Resources +
                '}';
      }
}
