import Courses.Certificate;
import FrontEnd.BrowseEnrollCourses;

import Courses.Course;
import Courses.Lesson;
import JSON.CertificatePDFGenerator;
import JSON.CertificateService;
import JSON.CourseService;
import JSON.InstructorManagment;

import JSON.JsonDatabaseManager;
import JSON.StudentService;
import JSON.UserService;
import Users.Student;
import Users.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
         try {
            // Initialize services
            UserService userService = new UserService("C:\\Users\\Mega Store\\Documents\\GitHub\\Prog2_lab8\\src\\data\\users.json");
            CourseService courseService = new CourseService("C:\\Users\\Mega Store\\Documents\\GitHub\\Prog2_lab8\\src\\data\\courses.json");
            StudentService studentService = new StudentService(userService, courseService, "C:\\Users\\Mega Store\\Documents\\GitHub\\Prog2_lab8\\src\\data\\students.json");
            InstructorManagment instructorManagment = new InstructorManagment(courseService, studentService, "C:\\Users\\Mega Store\\Documents\\GitHub\\Prog2_lab8\\src\\data\\instructors.json");
            CertificateService certificateService = new CertificateService("C:\\Users\\Mega Store\\Documents\\GitHub\\Prog2_lab8\\src\\data\\students.json");
            certificateService.generateCertificate(studentService.getStudentById("10046"),courseService.getCourseById("C001") );
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
