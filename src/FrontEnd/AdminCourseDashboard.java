package FrontEnd;

import Courses.Course;
import JSON.CourseService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// POJO Class for Course


public class AdminCourseDashboard extends JFrame {

    private JTable courseTable;
    private DefaultTableModel tableModel;
    private List<Course> courses = new ArrayList<>();
    CourseService courseService;
    public AdminCourseDashboard(CourseService courseService) {
        setTitle("Admin - Course Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        this.courseService = courseService;
        courses = courseService.getAllCourses();
        initTable();


        getContentPane().setBackground(new Color(100, 100, 100));
    }



    private void initTable() {
        String[] columns = {"ID", "Title", "Instructor ID", "Status", "Students", "Lessons"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Course c : courses) {
            Object[] row = {
                    c.getCourseId(),
                    c.getTitle(),
                    c.getInstructorId(),
                    getStatusWithColor(c.getStatus()),
                    c.getEnrolledStudentsCount(),
                    c.getLessonsCount()
            };
            tableModel.addRow(row);
        }

        courseTable = new JTable(tableModel);
        courseTable.setRowHeight(35);
        courseTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        courseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        courseTable.getTableHeader().setBackground(new Color(52, 73, 94));
//        courseTable.getTableHeader().setForeground(Color.WHITE);

        // Status column with colored labels
        courseTable.getColumnModel().getColumn(3).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel(value.toString());
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setOpaque(true);

            String status = value.toString().replaceAll("<[^>]*>", "").trim().toLowerCase();
            Color bg = switch (status) {
                case "accepted" -> new Color(39, 174, 96);
                case "pending" -> new Color(241, 196, 15);
                case "rejected" -> new Color(231, 76, 60);
                default -> Color.GRAY;
            };

            label.setBackground(isSelected ? table.getSelectionBackground() : bg);
            label.setForeground(Color.WHITE);
            label.setFont(new Font("Segoe UI", Font.BOLD, 12));
            return label;
        });

        // Double-click to view details
        courseTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = courseTable.getSelectedRow();
                    if (row != -1) {
                        showCourseDetails(courses.get(row));
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(courseTable);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton viewBtn = new JButton("View Details");
        viewBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        viewBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row != -1) {
                showCourseDetails(courses.get(row));
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        JButton approveBtn = new JButton("Approve Course");
        JButton declineBtn = new JButton("Decline Course");
        approveBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        approveBtn.setForeground(new Color(39, 174, 96));
        approveBtn.setFocusPainted(false);
        approveBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row != -1) {
                approveCourse(row);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });
        declineBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        declineBtn.setForeground(new Color(174, 39, 39));
        declineBtn.setFocusPainted(false);
        declineBtn.addActionListener(e -> {
            int row = courseTable.getSelectedRow();
            if (row != -1) {
                declineCourse(row);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a course to approve.", "No Selection", JOptionPane.WARNING_MESSAGE);
            }
        });

        buttonPanel.add(viewBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(approveBtn);
        buttonPanel.add(declineBtn);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private String getStatusWithColor(String status) {
        return switch (status.toLowerCase()) {
            case "accepted" -> "<html><font color='white'><b>Accepted</b></font></html>";
            case "pending" -> "<html><font color='#f39c12'><b>Pending</b></font></html>";
            case "rejected" -> "<html><font color='white'><b>Rejected</b></font></html>";
            default -> status;
        };
    }

    private void showCourseDetails(Course course) {
        JDialog detailDialog = new JDialog(this, "Course Details - " + course.getTitle(), true);
        detailDialog.setSize(500, 400);
        detailDialog.setLocationRelativeTo(this);
        detailDialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridx = 0;
        gbc.gridy = 0;

        addDetailRow(panel, gbc, "Course ID:", course.getCourseId());
        gbc.gridy++;
        addDetailRow(panel, gbc, "Title:", "<html><h2>" + course.getTitle() + "</h2></html>");
        gbc.gridy++;
        addDetailRow(panel, gbc, "Description:", "<html><i>" + course.getDescription() + "</i></html>");
        gbc.gridy++;
        addDetailRow(panel, gbc, "Instructor ID:", course.getInstructorId());
        gbc.gridy++;
        addDetailRow(panel, gbc, "Status:", getColoredStatusLabel(course.getStatus()));
        gbc.gridy++;
        addDetailRow(panel, gbc, "Total Lessons:", String.valueOf(course.getLessonsCount()));
        gbc.gridy++;
        addDetailRow(panel, gbc, "Enrolled Students:", String.valueOf(course.getEnrolledStudentsCount()));

        detailDialog.add(new JScrollPane(panel), BorderLayout.CENTER);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> detailDialog.dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn);
        detailDialog.add(btnPanel, BorderLayout.SOUTH);

        detailDialog.setVisible(true);
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, String label, String value) {
        gbc.gridx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(val, gbc);

        gbc.gridy++;
    }

    private void addDetailRow(JPanel panel, GridBagConstraints gbc, String label, JComponent component) {
        gbc.gridx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panel.add(lbl, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);

        gbc.gridy++;
    }

    private JLabel getColoredStatusLabel(String status) {
        JLabel label = new JLabel(status.toUpperCase());
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setOpaque(true);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);

        Color bg = switch (status.toLowerCase()) {
            case "accepted" -> new Color(39, 174, 96);
            case "pending" -> new Color(241, 196, 15);
            case "rejected" -> new Color(231, 76, 60);
            default -> Color.GRAY;
        };
        label.setBackground(bg);
        label.setPreferredSize(new Dimension(120, 35));
        return label;
    }
    private void approveCourse(int rowIndex) {
        Course course = courses.get(rowIndex);

        if (!"pending".equalsIgnoreCase(course.getStatus())) {
            JOptionPane.showMessageDialog(this,
                    "Only courses with 'Pending' status can be approved.",
                    "Cannot Approve", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Approve course: \"" + course.getTitle() + "\"?",
                "Confirm Approval",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Update in-memory object
            course.setStatus("accepted");
            courseService.save();

            // Update table row
            tableModel.setValueAt(getStatusWithColor("accepted"), rowIndex, 3);
            tableModel.setValueAt(course.getEnrolledStudentsCount(), rowIndex, 4);
            tableModel.setValueAt(course.getLessonsCount(), rowIndex, 5);

            JOptionPane.showMessageDialog(this,
                    "Course approved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);


        }
    }
    private void declineCourse(int rowIndex) {
        Course course = courses.get(rowIndex);

        if (!"pending".equalsIgnoreCase(course.getStatus())) {
            JOptionPane.showMessageDialog(this,
                    "Only courses with 'Pending' status can be declined.",
                    "Cannot decline", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Decline course: \"" + course.getTitle() + "\"?",
                "Confirm Approval",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            // Update in-memory object
            course.setStatus("rejected");
            courseService.save();

            // Update table row
            tableModel.setValueAt(getStatusWithColor("rejected"), rowIndex, 3);
            tableModel.setValueAt(course.getEnrolledStudentsCount(), rowIndex, 4);
            tableModel.setValueAt(course.getLessonsCount(), rowIndex, 5);

            JOptionPane.showMessageDialog(this,
                    "Course approved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);


        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            CourseService courseService1;
            try {
                courseService1 = new CourseService("src\\JSON\\courses.json");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           AdminCourseDashboard a = new AdminCourseDashboard(courseService1);
            a.setVisible(true);
            a.setLocationRelativeTo(null);
        });
    }
}