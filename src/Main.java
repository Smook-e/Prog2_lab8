
import FrontEnd.BrowseEnrollCourses;

import Courses.Course;
import Courses.Lesson;
import JSON.CourseService;

import JSON.JsonDatabaseManager;
import JSON.StudentService;
import JSON.UserService;
import Users.User;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Main {
    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not available", e);
        }
    }
    public static void main(String[] args) throws IOException {


       UserService users= new UserService("src\\JSON\\users.json");
       CourseService courseService = new CourseService("src\\JSON\\courses.json");
       StudentService studentService = new StudentService(users, courseService);

        List<Course> courses = studentService.browseCourses();
//        courseService.enrollStudent("C002", "10067");
//        courseService.save();
//
       ArrayList<Course> c =  courseService.getInstructorCourses("10813");
        for(Course course : c) {
            System.out.println(course.getCourseId());
            System.out.println(course.getTitle());
        }


    }
}

