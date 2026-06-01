package HospitalManagementSystem;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class ERManager {
    private PriorityQueue<Patient> erQueue;

    public ERManager(){
        // if Patient severity equal to 1 that patient will at the top of Heap & We use Comparator to set Min-Heap.
        erQueue = new PriorityQueue<>(Comparator.comparingInt(Patient::getSeverity));
    }

    // For New Patient
    public void admitToER(Patient p){
        erQueue.add(p);
        System.out.println("ER Alert: " + p.getName()+" admitted .(Severity: "+p.getSeverity()+")");
    }

    // Treat next patient and kick from queue. hhhhhhhh
    public Patient treatNextPatient(){
        if(erQueue.isEmpty()){
            System.out.println("ER is Empty treat next patient Plz!");
            return null;
        }
        Patient p = erQueue.poll();
        System.out.println("Doctor is treating "+p.getName());
        return p;
    }

    // Just to check Patient in ER
    public Patient checkNextPatient(){
        return erQueue.peek();
    }

    //List of Patients
    public List<Patient> getAllERWaitingPatients(){
        // Sort the patients
        PriorityQueue<Patient> tempQueue = new PriorityQueue<>(erQueue);
        List<Patient> waitingList = new ArrayList<>();

        while (!tempQueue.isEmpty()) {
            waitingList.add(tempQueue.poll());
        }
        return waitingList;
    }

    public boolean isEREmpty(){
        return erQueue.isEmpty();
    }

}
