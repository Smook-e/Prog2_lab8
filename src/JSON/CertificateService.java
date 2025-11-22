package JSON;

import Courses.Certificate;
import Courses.Course;
import Users.Student;
import Users.User;
import java.io.IOException;

public class CertificateService extends JsonDatabaseManager<User> {

    public CertificateService(String fileName) throws IOException {
        super(fileName, User.class);
    }

    public Certificate generateCertificate(Student student, Course course) {
        String certId = "CERT-" + System.currentTimeMillis();
        String date = java.time.LocalDate.now().toString();

        Certificate cert = new Certificate(certId, student.getUserID(), course.getCourseId(), date);
        if (SearchStudent(student.getUserID()) == -1) {
            return null;
        }
        student.getCertificates().add(cert);
        db.add(SearchStudent(student.getUserID()), student);
        save();
        CertificatePDFGenerator.generate(cert, student, course);
        return cert;
    }

    public int SearchStudent(String id) {
        int i = 0;
        for (User u : db) {
            if (u instanceof Student) {
                if (u.getUserID().equals(id)) {
                    return i;
                }
                i++;
            }

        }
        return -1;
    }

    public Certificate getCertificateById(String certId) {
        for (User u : db) {
            if (u instanceof Student) {
                Student s = (Student) u;
                for (Certificate c : s.getCertificates()) {
                    if (c.getCertificateId().equals(certId)) {
                        return c;
                    }
                }
            }
        }
        return null;
    }

}
