package HospitalManagementSystem;

public class Patient {
    private int id;
    private String name;
    private int severity;
    private String condition;
    private String contact;

    public Patient(int id,String name, int severity,String condition, String contact ){
        this.id = id;
        this.name = name;
        this.severity = severity;
        this.condition = condition;
        this.contact = contact;
    }

    // Getter and Setters
    public int getId(){
        return this.id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getSeverity(){
        return this.severity;
    }
    public void setSeverity(int severity){
        this.severity = severity;
    }

    public String getCondition(){
        return this.condition;
    }
    public void setCondition(String condition){
        this.condition = condition;
    }

    public String getContact(){
        return this.contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }

    @Override
    public String toString(){
        return String.format("ID: %d | Name: %-10s | Severity: %d | Condition: %s", id, name, severity, condition);
    }
}
