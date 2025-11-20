/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package JSON;

import Courses.Certificate;
import Courses.Course;
import Users.User;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;

public class CertificatePDFGenerator {

    public static void generate(Certificate cert, User student, Course course) {

        try {
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(cert.getCertificateId() + ".pdf"));
            doc.open();         
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.BOLD);
            Paragraph p = new Paragraph("Certificate", titleFont);
            p.setAlignment(Element.ALIGN_CENTER);
            doc.add(p);        
            Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD);
            Font bodyFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.NORMAL);
            doc.add(new Paragraph("\n\nStudentName: ",subFont));
            doc.add(new Paragraph(student.getUserName(),bodyFont));
            doc.add(new Paragraph("CourseTitle: ",subFont));
            doc.add(new Paragraph(course.getTitle(),bodyFont));
            doc.add(new Paragraph(("\n\nThis certifies that "+student.getUserName()+" Has successfully completed the course: "+course.getTitle()),bodyFont));         
            doc.add(new Paragraph("\n\nIssue Date: ",subFont));
            doc.add(new Paragraph(cert.getIssueDate(),bodyFont));
            doc.add(new Paragraph("Certificate ID: ",subFont));
            doc.add(new Paragraph(cert.getCertificateId(),bodyFont));
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
