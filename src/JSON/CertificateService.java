package JSON;

import Courses.Certificate;
import Courses.Course;
import Users.Student;
import Users.User;
import java.io.IOException;

public class CertificateService extends JsonDatabaseManager<Student> {

    public CertificateService(String fileName) throws IOException {
        super(fileName, Student.class);
    }

    public Certificate generateCertificate(Student student, Course course) {
        String certId = "CERT-" + System.currentTimeMillis();
        String date = java.time.LocalDate.now().toString();

        Certificate cert = new Certificate(certId, student.getUserID(), course.getCourseId(), date);
        if (SearchStudent(student.getUserID()) == -1) {
            return null;
        }
        student.getCertificates().add(cert);
        db.set(SearchStudent(student.getUserID()), student);
        save();
        CertificatePDFGenerator.generate(cert, student, course);
        return cert;
    }

    public int SearchStudent(String id) {
        int i = 0;
        for (Student s: db) {
                if (s.getUserID().equals(id)) {
                    return i;
                }
                i++;
            }
        return -1;
    }

    public Certificate getCertificateById(String certId) {
        for (Student s : db) {
                for (Certificate c : s.getCertificates()) {
                    if (c.getCertificateId().equals(certId)) {
                        return c;
                    }
                }
        }
        return null;
    }

}
