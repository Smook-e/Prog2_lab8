import Users.*;
import Courses.*;
import JSON.*;
import java.util.*;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        // ======= FILE PATHS =======
        String usersFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\users.json";
        String coursesFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\courses.json";
        String studentsFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\students.json";
        String instructorsFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\instructors.json";

        // ======= SERVICES =======
        UserService userService = new UserService(usersFile);
        CourseService courseService = new CourseService(coursesFile);
        StudentService studentService = new StudentService(userService, courseService, studentsFile);
        InstructorManagment instructorService = new InstructorManagment(courseService, studentService, instructorsFile);

        // ======= CREATE USERS =======
        Student student = new Student("S001",  "pass123","Alice", "alice@example.com");
        student.setStudentService(studentService);
        studentService.addStudent(student);

        Instructor instructor = new Instructor("I001", "pass456","Bob", "bob@example.com");
        instructor.setInstructorManagment(instructorService);
        instructorService.addInstructor(instructor, userService);

        // ======= CREATE COURSE =======
        instructorService.createCourse(instructor, "C500", "Programming", "Learn Java Basics");
        Course course = courseService.getCourseById("C500");

        System.out.println("Created course: " + course.getTitle());

        // ======= ADD LESSONS =======
        Lesson lesson1 = new Lesson("L001", "Variables", "Java Variables content");
        Lesson lesson2 = new Lesson("L002", "Loops", "Java Loops content");
        course.addLesson(lesson1);
        course.addLesson(lesson2);
        courseService.save();

        System.out.println("Added lessons: " + course.getLessonsCount());

        // ======= ENROLL STUDENT =======
        studentService.enrollStudentInCourse(student, "C001");
        System.out.println("Student enrolled courses: " + student.getEnrolledCourses());

        // ======= CREATE QUIZ =======
        Quiz quiz = new Quiz("Q001");
        quiz.addQuestionIdS("Q1");
        quiz.addQuestionIdS("Q2");
        quiz.addQuestionIdS("Q3");
        lesson1.setQuiz(quiz);

        // ======= SUBMIT QUIZ =======
        List<Integer> answers = Arrays.asList(1, 0, 1); // student answers
        int score = studentService.submitQuiz(student, "L001", lesson1.getQuiz(), answers);
        System.out.println("Quiz score: " + score);

        // ======= MARK LESSON COMPLETED =======
        boolean completed = studentService.markLessonCompleted(student, "C500", "L001");
        System.out.println("Lesson completed: " + completed);

        // ======= STUDENT PROGRESS =======
        System.out.println("Student progress: " + student.getProgress());

        // ======= INSTRUCTOR EDIT COURSE =======
        System.out.println("\n=== Instructor editing course ===");
        course.setTitle("Advanced Java Programming");
        boolean updated = instructorService.updateCourse(instructor, course);
        System.out.println("Course updated? " + updated);
        System.out.println("Course new title: " + courseService.getCourseById("C500").getTitle());

        // ======= INSTRUCTOR LESSON AVERAGE =======
        double avg = instructorService.getLessonAverage("C500", "L001");
        System.out.println("Lesson average: " + avg);

        // ======= COURSE COMPLETION % =======
        double completion = instructorService.getStudentCourseCompletionPercentage("S001", "C500");
        System.out.println("Course completion %: " + completion);

    }
}
