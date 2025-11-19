import Users.*;
import Courses.*;
import JSON.*;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception {
        // --- Initialize Services & Managers ---
     String usersFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\users.json";
String coursesFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\courses.json";
String studentsFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\students.json";
String instructorsFile = "C:\\Users\\USER\\Documents\\NetBeansProjects\\Prog2_lab8\\src\\data\\instructors.json";


        UserService userService = new UserService(usersFile);
        CourseService courseService = new CourseService(coursesFile);

        // StudentService now takes file path
        StudentService studentService = new StudentService(userService, courseService, studentsFile);

        // InstructorManagment now takes file path
        InstructorManagment instructorManagment = new InstructorManagment(courseService, studentService, instructorsFile);

        // --- Create Instructor ---
        Instructor inst = new Instructor("I001", "Alice", "alice@example.com", "password123");
        inst.setInstructorManagment(instructorManagment);
        instructorManagment.addInstructor(inst, userService); // adds to instructors.json & users.json

        // --- Instructor creates a Course & Lesson ---
        String courseId = "C001";
        String lessonId = "L001";
        instructorManagment.createCourse(inst, courseId, "Java Basics", "Intro to Java");
        instructorManagment.createLesson(inst, courseId, lessonId, "Lesson 1: Variables", "Content here");

        // --- Create Student ---
        Student student = new Student("S001", "Bob", "bob@example.com", "pass456");
        student.setStudentService(studentService);
        studentService.addStudent(student); // adds to students.json & users.json

        // --- Enroll student in course ---
        student.enrollCourse(courseId);

        // --- Create a Quiz ---
        Quiz quiz = new Quiz(lessonId, Arrays.asList(1, 2, 3)); // correct answers

        // --- Student submits the Quiz ---
        List<Integer> studentAnswers = Arrays.asList(1, 2, 3); // perfect score
        int score = student.takeQuiz(lessonId, quiz, studentAnswers);

        System.out.println("Quiz score: " + score);

        // --- Student marks Lesson complete ---
        boolean completed = student.markLessonCompleted(courseId, lessonId);
        System.out.println("Lesson completed: " + completed);

        // --- Check Student progress ---
        System.out.println("Student progress: " + student.getProgress());
        System.out.println("Student enrolled courses: " + student.getEnrolledCourses());

        // --- Lesson average across all students ---
        double average = instructorManagment.getLessonAverage(courseId, lessonId);
        System.out.println("Lesson average: " + average);

        // --- Student course completion percentage ---
        double completion = instructorManagment.getStudentCourseCompletionPercentage(student.getUserID(), courseId);
        System.out.println("Course completion %: " + completion);
    }
}
