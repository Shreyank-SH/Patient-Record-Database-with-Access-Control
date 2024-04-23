import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class HealthMonitoringGUI extends JFrame {
    private JTextField nameField;
    private JComboBox<String> userTypeCombo;
    private JButton loginButton;
    private User currentUser;
    public static List<Window> openWindows = new ArrayList<>();
    private JButton signupButton;
    private JPasswordField passwordField;
    private JButton changeModeButton;

    public HealthMonitoringGUI() {
        setTitle("Health Monitoring System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create login panel components
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10); // Add padding around components, except for bottom padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(new JLabel("Name:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        nameField = new JTextField();
        nameField.setColumns(15); // Increase the width of the name field
        loginPanel.add(nameField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 10); // Remove left and bottom padding for buttons
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        loginButton.setEnabled(false);
        loginButton.setPreferredSize(new Dimension(100, 25)); // Set the preferred size for the "Login" button
        loginPanel.add(loginButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 0, 10); // Reset insets for labels
        loginPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        passwordField = new JPasswordField();
        passwordField.setColumns(15); // Increase the width of the password field
        passwordField.setEnabled(false);
        loginPanel.add(passwordField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 0, 10); // Remove left and bottom padding for buttons
        signupButton = new JButton("Sign In");
        signupButton.addActionListener(new SignupButtonListener());
        signupButton.setEnabled(false);
        signupButton.setPreferredSize(new Dimension(100, 25)); // Set the preferred size for the "Sign In" button
        loginPanel.add(signupButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 0, 10); // Reset insets for labels
        loginPanel.add(new JLabel("User Type:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        userTypeCombo = new JComboBox<>(new String[]{"Select Mode", "Family Member", "Doctor"});
        userTypeCombo.addItemListener(new UserTypeComboListener());
        loginPanel.add(userTypeCombo, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 0, 10); // Remove left and bottom padding for buttons
        changeModeButton = new JButton("Change Mode");
        changeModeButton.addActionListener(new ChangeModeButtonListener());
        changeModeButton.setPreferredSize(new Dimension(120, 25)); // Set the preferred size for the "Change Mode" button
        loginPanel.add(changeModeButton, gbc);

        // Add components to the frame
        getContentPane().add(loginPanel, BorderLayout.CENTER);
        setSize(400, 180); // Increase the width of the frame to accommodate the wider fields
        setLocationRelativeTo(null);
        setVisible(true);
        openWindows.add(this);
    }

    private class UserTypeComboListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedUserType = (String) userTypeCombo.getSelectedItem();
                if (selectedUserType.equals("Doctor")) {
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(true);
                    passwordField.setEnabled(true); // Enable password field for doctors
                } else if (selectedUserType.equals("Family Member")) {
                    loginButton.setEnabled(true);
                    signupButton.setEnabled(false);
                    passwordField.setEnabled(false); // Disable password field for family members
                } else if (selectedUserType.equals("Select Mode")) {
                    loginButton.setEnabled(false);
                    signupButton.setEnabled(false);
                    passwordField.setEnabled(false); // Disable password field when "Select Mode" is selected
                }

                // Remove "Select Mode" from the combo box after a selection has been made
                if (!selectedUserType.equals("Select Mode")) {
                    for (int i = 0; i < userTypeCombo.getItemCount(); i++) {
                        if (userTypeCombo.getItemAt(i).equals("Select Mode")) {
                            userTypeCombo.removeItemAt(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    private class SignupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new Doctor.DoctorSignup(); // Open the DoctorSignup window
        }
    }

    private class ChangeModeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Close all open windows
            for (Window window : openWindows) {
                window.dispose();
            }
            openWindows.clear();

            // Open the starting GUI
            new HealthMonitoringGUI();
        }
    }

    private int getRecordIdFromUser() {
        String input = JOptionPane.showInputDialog("Enter Record ID:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid integer.");
            return -1;
        }
    }

    private String getDoctorNameFromUser() {
        return JOptionPane.showInputDialog("Enter Doctor Name:");
    }

    private JPanel createFamilyMemberPanel() {
        JPanel panel = new JPanel();
        JButton viewButton = new JButton("View Record");
        JButton addButton = new JButton("Add Record");
        JButton updateButton = new JButton("Update Record");
        JButton deleteButton = new JButton("Delete Record");
        openWindows.add(this);
        viewButton.setEnabled(true);
        addButton.setEnabled(false);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        viewButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            String doctorName = getDoctorNameFromUser();
            if (id != -1 && doctorName != null) {
                DatabaseManager.getRecord(id, doctorName);
            }
        });

        addButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                HealthRecord record = getRecordDetailsFromUser(id);
                if (record != null) {
                    DatabaseManager.insertRecord(id, record, currentUser.getName());
                }
            }
        });

        updateButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                HealthRecord record = getRecordDetailsFromUser(id);
                if (record != null) {
                    DatabaseManager.updateRecord(id, record, currentUser.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "Record not found. Please enter a valid ID.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                DatabaseManager.deleteRecord(id, currentUser.getName());
            }
        });

        panel.add(viewButton);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        return panel;
    }

    private JPanel createDoctorPanel() {
        JPanel panel = new JPanel();
        JButton viewButton = new JButton("View Record");
        JButton addButton = new JButton("Add Record");
        JButton updateButton = new JButton("Update Record");
        JButton deleteButton = new JButton("Delete Record");
        openWindows.add(this);
        viewButton.setEnabled(true);
        addButton.setEnabled(true);
        updateButton.setEnabled(true);
        deleteButton.setEnabled(true);

        viewButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                DatabaseManager.getRecord(id, currentUser.getName());
            }
        });

        addButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                HealthRecord record = getRecordDetailsFromUser(id);
                if (record != null) {
                    DatabaseManager.insertRecord(id, record, currentUser.getName());
                }
            }
        });

        updateButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                HealthRecord record = getRecordDetailsFromUser(id);
                if (record != null) {
                    DatabaseManager.updateRecord(id, record, currentUser.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "Record not found. Please enter a valid ID.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int id = getRecordIdFromUser();
            if (id != -1) {
                DatabaseManager.deleteRecord(id, currentUser.getName());
            }
        });

        panel.add(viewButton);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        return panel;
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String userType = (String) userTypeCombo.getSelectedItem();

            if (userType.equals("Family Member")) {
                currentUser = new FamilyMember(name);
            } else if (userType.equals("Doctor")) {
                String password = new String(passwordField.getPassword());
                if (verifyDoctorCredentials(name, password)) {
                    currentUser = new Doctor(name);
                } else {
                    JOptionPane.showMessageDialog(null, "Name and password do not match. Please try again.");
                    nameField.setText("");
                    passwordField.setText("");
                    return;
                }
            }

            // Open the main application window
            MainWindow mainWindow = new MainWindow(currentUser);
            if (currentUser instanceof FamilyMember) {
                mainWindow.getContentPane().add(createFamilyMemberPanel());
            } else {
                mainWindow.getContentPane().add(createDoctorPanel());
            }
            mainWindow.pack();
            mainWindow.setVisible(true);
        }

        private boolean verifyDoctorCredentials(String name, String password) {
            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM doctor_verify WHERE name = ? AND password = ?")) {
                stmt.setString(1, name);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();
                return rs.next();
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    private HealthRecord getRecordDetailsFromUser(int id) {
        String patientName = JOptionPane.showInputDialog("Enter Patient Name:");
        String diagnosis = JOptionPane.showInputDialog("Enter Diagnosis:");
        String treatment = JOptionPane.showInputDialog("Enter Treatment:");
        return new HealthRecord(id, patientName, diagnosis, treatment, currentUser.getName());
    }

    private class MainWindow extends JFrame {
        public MainWindow(User user) {
            super("Health Monitoring System - " + user.name);

            // Create main application window components
            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(e -> System.exit(0));
            fileMenu.add(exitMenuItem);
            menuBar.add(fileMenu);
            setJMenuBar(menuBar);

            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setVisible(true);
            openWindows.add(this);
        }
    }

    public static void main(String[] args) {
        new HealthMonitoringGUI();
    }
}