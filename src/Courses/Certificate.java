package Courses;

public class Certificate {

    private final String certificateId;
    private final String studentId;
    private final String courseId;
    private final String issueDate;

    public Certificate(String certificateId, String studentId, String courseId, String issueDate) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.issueDate = issueDate;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getIssueDate() {
        return issueDate;
    }
}
