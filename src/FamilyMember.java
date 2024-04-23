import javax.swing.*;
class FamilyMember extends User {
    public FamilyMember(String name) {
        super(name);
    }
    public void addRecord(HealthRecord record, String doctorName) {
        JOptionPane.showMessageDialog(null, "Don't have rights to it");
    }

    @Override
    public void viewRecord(int id, String doctorName) {
        DatabaseManager.getRecord(id, this.getName());
    }

    @Override
    public void updateRecord(int id, HealthRecord record, String doctorName) {
        JOptionPane.showMessageDialog(null, "Don't have rights to it");
    }

    @Override
    public void deleteRecord(int id, String doctorName) {
        JOptionPane.showMessageDialog(null, "Don't have rights to it");
    }
}