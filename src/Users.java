import java.util.ArrayList;
import java.util.List;

interface AccessControl {
    public abstract void viewRecord(int id, String doctorName);
    public abstract void updateRecord(int id, HealthRecord record, String doctorName);
    public abstract void deleteRecord(int id, String doctorName);
}

class UnauthorizedAccessException extends Exception {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}

class User implements AccessControl {
    protected String name;
    protected List<HealthRecord> accessibleRecords;

    public User(String name) {
        this.name = name;
        this.accessibleRecords = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void addRecord(HealthRecord record) {
        // Default implementation
    }

    public void viewRecord(int id) {
        // Default implementation
    }

    public void updateRecord(int id, HealthRecord record) {
        // Default implementation
    }

    public void deleteRecord(int id) {
        // Default implementation
    }

    @Override
    public void viewRecord(int id, String doctorName) {
        // Default implementation
    }

    @Override
    public void updateRecord(int id, HealthRecord record, String doctorName) {
        // Default implementation
    }

    @Override
    public void deleteRecord(int id, String doctorName) {
        // Default implementation
    }
}