package FrontEnd;
import FrontEnd.CourseManagementStudent;
import FrontEnd.InstructorDashboard;
import JSON.StudentService;
import JSON.CourseService;
import JSON.InstructorManagment;
import JSON.StudentService;
import JSON.CourseService;
import Users.Student;
import JSON.UserService;
import Users.Instructor;
import Users.User;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import FrontEnd.InstructorDashboard;



public class SignInApp {
    static UserService users;
    static CourseService courseService;
    static StudentService studentService;

    static {
        try {
            users = new UserService("src\\JSON\\users.json");
            courseService = new CourseService("src\\JSON\\courses.json");
            studentService = new StudentService(users, courseService);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (IOException e) {
            System.out.println("Error loading users!");

        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public SignInApp() throws IOException {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignInApp::showSignInWindow);
    }

    // ===================== SIGN IN WINDOW =====================
    public static void showSignInWindow() {
        JFrame frame = new JFrame("Sign In");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 250);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 25));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton signInBtn = new JButton("Sign In");
        JButton registerBtn = new JButton("Register");


        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(signInBtn);
        panel.add(registerBtn);
        passField.addActionListener(e -> {
            String username = userField.getText().trim();
            String password = new String(passField.getPassword());
            try {
                handleSignIn(username, password, frame);
            } catch (UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        signInBtn.addActionListener(e -> {
            try {
                handleSignIn(userField.getText().trim(), new String(passField.getPassword()), frame);
            } catch (UnsupportedLookAndFeelException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InstantiationException ex) {
                throw new RuntimeException(ex);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });
        registerBtn.addActionListener(e -> showRegisterWindow(frame));

        frame.add(panel);
        frame.setVisible(true);
    }

    // ===================== REGISTER WINDOW (ALL FIELDS) =====================
    private static void showRegisterWindow(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Register New User", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField userNameField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JPasswordField confirmField = new JPasswordField();
        JTextField roleField = new JTextField("Student"); // Default role
        JButton createBtn = new JButton("Create Account");
        JRadioButton option1 = new JRadioButton("Instructor");
        JRadioButton option2 = new JRadioButton("Student", true);
        ButtonGroup group = new ButtonGroup();
        ;
        group.add(option1);
        group.add(option2);



        panel.add(new JLabel("Username:"));
        panel.add(userNameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);
        panel.add(new JLabel("Confirm Password:"));
        panel.add(confirmField);

        panel.add(option1);
        panel.add(option2);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(createBtn);

        createBtn.addActionListener(e -> {
            String username = userNameField.getText().trim();
            String email = emailField.getText().trim();
            String pass1 = new String(passField.getPassword());
            String pass2 = new String(confirmField.getPassword());
            String role = option2.isSelected() ? "Student" : "Instructor";

            // === VALIDATION ===
            if (username.isEmpty() || email.isEmpty() || pass1.isEmpty()) {
            }
            if (!isValid(email)) {
                JOptionPane.showMessageDialog(dialog, "Enter a valid email!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (usernameExists(username)) {
                JOptionPane.showMessageDialog(dialog, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            //create ID
            Random rand = new Random();
            int randomNum = rand.nextInt(1000) +10000;
            while(users.containsID(Integer.toString(randomNum))){
                randomNum = rand.nextInt(1000) +10000;
            }
            String userID = Integer.toString(randomNum);
            String hashedPassword = hashPassword(pass1);
            // === ADD NEW USER ===
            users.addUser(new User(userID, hashedPassword, username, role, email));
            users.save();

            JOptionPane.showMessageDialog(dialog, "Account created successfully!\nID: " + userID, "Success", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        dialog.add(panel);
        dialog.setVisible(true);
    }
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

    // ===================== SIGN IN LOGIC =====================
   private static void handleSignIn(String username, String password, JFrame frame) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String hashedPassword = hashPassword(password);

    if (validateLogin(username, hashedPassword)) {
        JOptionPane.showMessageDialog(frame, "Welcome, " + username + "!", "Success", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();

        User u = users.getUserByUsername(username);

        if (u.getRole().equalsIgnoreCase("Student")) {
            // Option 2: safely create Student object if needed
            Student s;
            if (u instanceof Student) {
                s = (Student) u;
            } else {
                s = new Student(u.getUserID(), u.getUserName(), u.getEmail(), u.getPassword());
                s.setStudentService(studentService);
            }
            studentDashboard(s);
            frame.dispose();

        } else if (u.getRole().equalsIgnoreCase("Instructor")) {
            Instructor inst;
            if (u instanceof Instructor) {
                inst = (Instructor) u;
            } else {
                inst = new Instructor(u.getUserID(), u.getUserName(), u.getEmail(), u.getPassword());
            }


            InstructorManagment instructorManagment=new InstructorManagment(courseService, studentService);
            inst.setInstructorManagment(instructorManagment);


            InstructorDashboard dashboard = new InstructorDashboard(instructorManagment,inst);
            dashboard.setVisible(true);
            dashboard.setLocationRelativeTo(null);

            // Open CourseManagementStudent frame
            frame.dispose();


        } else {

                AdminCourseDashboard a = new AdminCourseDashboard(courseService);
                a.setVisible(true);
                a.setLocationRelativeTo(null);
                frame.dispose();
        }

    } else {
        JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
}



    public static void studentDashboard(Student s) {
        JFrame main = new JFrame("studentDashboard");
        main.setSize(500, 400);
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton browseButton = new JButton("Browse Courses");
        JButton signOutBtn = new JButton("Sign Out");
        signOutBtn.addActionListener(e -> {
            main.dispose();
            showSignInWindow();
        });
        browseButton.addActionListener(e -> {
            BrowseEnrollCourses b =    new BrowseEnrollCourses(s, studentService, courseService);

            b.setVisible(true);
            b.setLocationRelativeTo(null);
            main.dispose();

        });

        panel.add(browseButton);
        panel.add(new JLabel());
        panel.add(signOutBtn);
        main.add(panel);
        main.setVisible(true);
    }
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$";

    private static final java.util.regex.Pattern pattern =
            java.util.regex.Pattern.compile(EMAIL_REGEX);

    public static boolean isValid(String email) {
        if (email == null) return false;
        return pattern.matcher(email).matches();
    }

    // ===================== VALIDATION =====================
    private static boolean validateLogin(String username, String password) {
        ArrayList<User> userList = users.getDb();

        for (User u : userList) {
            if (u.getUserName().equals(username) && u.getPassword().equals(password)) {
                return true;
            }
        }
        return false;   }


    private static boolean usernameExists(String username) {
        return users.getDb().stream().anyMatch(u -> u.getUserName().equals(username));
    }
}








