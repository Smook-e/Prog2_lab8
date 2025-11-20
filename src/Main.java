
import Courses.Certificate;
import FrontEnd.BrowseEnrollCourses;

import Courses.Course;
import Courses.Lesson;
import JSON.CertificatePDFGenerator;
import JSON.CourseService;

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
        CertificatePDFGenerator c1 = new CertificatePDFGenerator();
        Course course = new Course("1233", "math5", "lablace", "1234");
        Student student = new Student("123", "goo", "rrr@gmail.com", "gdf");
        Certificate cert = new Certificate("1234", "123", "1233", "2025/11/20");
        c1.generate(cert,student,course);
    }
}

