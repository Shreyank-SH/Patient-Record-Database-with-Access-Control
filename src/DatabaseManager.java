import java.sql.*;
import javax.swing.*;
import java.awt.*;
public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/health_records";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Shreyank12";

    public static Connection getConnection() throws SQLException {
        try {
            DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            if (e.getMessage().contains("Unknown database")) {
                createDatabase();
            }
        }
        createDatabase();
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private static void createDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS health_records");
            stmt.executeUpdate("USE health_records");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS health_records (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "patient_name VARCHAR(255)," +
                    "diagnosis VARCHAR(255)," +
                    "treatment VARCHAR(255)," +
                    "notes VARCHAR(255)" +
                    ")");
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS doctor_verify (" +
                    "name VARCHAR(255) PRIMARY KEY," +
                    "password VARCHAR(255)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertRecord(int id, HealthRecord record, String doctorName) {
        try (Connection conn = getConnection()) {
            String tableName = doctorName.replace(" ", "_"); // Replace spaces with underscores for table name
            createTableIfNotExists(tableName); // Create table if it doesn't exist

            String sql = "INSERT INTO " + tableName + " (id, patient_name, diagnosis, treatment) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, record.getId());
            statement.setString(2, record.getPatientName());
            statement.setString(3, record.getDiagnosis());
            statement.setString(4, record.getTreatment());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void getRecord(int id, String doctorName) {
        HealthRecord record = null;
        try (Connection conn = getConnection()) {
            String tableName = doctorName.replace(" ", "_");
            String sql = "SELECT * FROM " + tableName + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String patientName = resultSet.getString("patient_name");
                String diagnosis = resultSet.getString("diagnosis");
                String treatment = resultSet.getString("treatment");
                record = new HealthRecord(id, patientName, diagnosis, treatment, doctorName); // pass the doctor's name
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while fetching the record: " + e.getMessage());
            return;
        }

        if (record != null) {
            JFrame frame = new JFrame("Record Details");
            JTextArea textArea = new JTextArea(record.toString());
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
            frame.setSize(300, 200);
            frame.setVisible(true);
            Patient_Record_With_Access_Control.openWindows.add(frame);
        } else {
            JOptionPane.showMessageDialog(null, "No record found with ID: " + id);
        }
    }

    public static void updateRecord(int id, HealthRecord record, String doctorName) {
        try (Connection conn = getConnection()) {
            String tableName = doctorName.replace(" ", "_");
            String sql = "UPDATE " + tableName + " SET patient_name = ?, diagnosis = ?, treatment = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, record.getPatientName());
            statement.setString(2, record.getDiagnosis());
            statement.setString(3, record.getTreatment());
            statement.setInt(4, id);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Record updated successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteRecord(int id, String doctorName) {
        try (Connection conn = getConnection()) {
            String tableName = doctorName.replace(" ", "_");
            String sql = "DELETE FROM " + tableName + " WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Record deleted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void createTableIfNotExists(String tableName) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT," +
                    "patient_name VARCHAR(255)," +
                    "diagnosis VARCHAR(255)," +
                    "treatment VARCHAR(255)," +
                    "notes VARCHAR(255)" +
                    ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}