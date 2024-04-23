import javax.swing.*;
import java.awt.*;
import java.sql.*;

class Doctor extends User {
    public Doctor(String name) {
        super(name);
    }

    public void addRecord(HealthRecord record) {
        JOptionPane.showMessageDialog(null, "Please provide a doctor's name to add a record.");
    }

    public void addRecord(HealthRecord record, String doctorName) {
        int id = record.getId();
        DatabaseManager.insertRecord(id, record, doctorName);
    }

    public void viewRecord(int id) {
        JOptionPane.showMessageDialog(null, "Please provide a doctor's name to view a record.");
    }

    @Override
    public void viewRecord(int id, String doctorName) {
        DatabaseManager.getRecord(id, doctorName);
    }

    public void updateRecord(int id, HealthRecord record) {
        JOptionPane.showMessageDialog(null, "Please provide a doctor's name to update a record.");
    }

    @Override
    public void updateRecord(int id, HealthRecord record, String doctorName) {
        DatabaseManager.updateRecord(id, record, doctorName);
    }

    @Override
    public void deleteRecord(int id, String doctorName) {
        DatabaseManager.deleteRecord(id, doctorName);
    }

    public void deleteRecord(int id) {
        JOptionPane.showMessageDialog(null, "Please provide a doctor's name to delete a record.");
    }

    static class DoctorSignup extends JFrame {
        private JTextField nameField;
        private JPasswordField passwordField, confirmPasswordField;
        private JButton signupButton;

        public DoctorSignup() {
            setTitle("Doctor Sign Up");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLayout(new GridLayout(4, 2));

            add(new JLabel("Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Password:"));
            passwordField = new JPasswordField();
            add(passwordField);

            add(new JLabel("Confirm Password:"));
            confirmPasswordField = new JPasswordField();
            add(confirmPasswordField);

            signupButton = new JButton("Sign Up");
            signupButton.addActionListener(e -> handleSignup());
            add(signupButton);

            pack();
            setLocationRelativeTo(null);
            setVisible(true);
        }

        private void handleSignup() {
            String name = nameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (password.equals(confirmPassword)) {
                try (Connection conn = DatabaseManager.getConnection();
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO doctor_verify (name, password) VALUES (?, ?)")) {
                    stmt.setString(1, name);
                    stmt.setString(2, password);
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Sign up successful!");
                    dispose(); // Close the sign-up window
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error signing up: " + ex.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Passwords do not match!");
                passwordField.setText("");
                confirmPasswordField.setText("");
            }
        }
    }
}