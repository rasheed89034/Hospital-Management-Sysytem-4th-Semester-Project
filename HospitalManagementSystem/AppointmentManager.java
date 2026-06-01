package HospitalManagementSystem;
import java.util.LinkedList;
import java.util.List;

public class AppointmentManager {
    private LinkedList<Patient> appointments;

    public AppointmentManager() {
        appointments = new LinkedList<>();
    }

    // New appointment will add in last.
    public void scheduleAppointment(Patient p) {
        if (p != null) {
            appointments.add(p);
            System.out.println("Appointment Booked: " + p.getName() + " (ID: " + p.getId() + ")");
        }
    }

    // appointment will cancel based on Patient ID.
    public boolean cancelAppointment(int patientId) {
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getId() == patientId) {
                appointments.remove(i);
                System.out.println("Appointment Cancelled: Patient ID " + patientId);
                return true;
            }
        }
        System.out.println("Error: ID " + patientId + " ka koi appointment nahi mila.");
        return false;
    }

    public Patient serveNextAppointment() {
        if (!appointments.isEmpty()) {
            return appointments.pollFirst();
        }
        return null;
    }

    // How many Patients are waiting.
    public int getWaitingCount() {
        return appointments.size();
    }

    public List<Patient> getFullSchedule() {
        return appointments;
    }

    public void insertEmergencyAppointment(int position, Patient p) {
        if (position >= 0 && position <= appointments.size()) {
            appointments.add(position, p);
            System.out.println("Emergency Slot Allocated to: " + p.getName());
        }
    }
}
