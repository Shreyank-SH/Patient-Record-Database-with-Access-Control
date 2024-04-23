class HealthRecord {
    private int id;
    private String patientName;
    private String diagnosis;
    private String treatment;
    private String doctorName;

    public HealthRecord(int id, String patientName, String diagnosis, String treatment,String doctorName) {
        this.id = id;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.doctorName = doctorName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public String getDoctorName() {
        return doctorName;
    }

    // Setters (optional, if you need to modify the record)
    public void setId(int id) {
        this.id = id;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Patient Name: " + patientName + "\n" +
                "Diagnosis: " + diagnosis + "\n" +
                "Treatment: " + treatment + "\n" +
                "Doctor: " + doctorName; // include the doctor's name in the output
    }
}