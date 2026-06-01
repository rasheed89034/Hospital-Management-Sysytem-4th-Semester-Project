package HospitalManagementSystem;
import java.util.ArrayList;
import java.util.Stack;
import java.util.List;

public class HistoryManager {
    private Stack<Patient> historyStack;
    public HistoryManager(){
        historyStack = new Stack<>();
    }

    // Patient after treatment, push it in history.
    public void addVisitToHistory(Patient p){
        if(p != null){
            historyStack.push(p);
            System.out.println("History Updated: " + p.getName() + " Record save in history.");
        }
    }

    // If record added by mistake so undo that record.
    public Patient undoLastVisit() {
        if (!historyStack.isEmpty()) {
            Patient p = historyStack.pop();
            System.out.println("Undo Successful: " + p.getName() + " ka record history se hata diya gaya.");
            return p;
        }
        System.out.println("History khali hai.");
        return null;
    }

    public Patient getLastTreatedPatient() {
        if (!historyStack.isEmpty()) {
            return historyStack.peek();
        }
        return null;
    }

    public List<Patient> getFullHistory() {
        List<Patient> historyList = new ArrayList<>(historyStack);
        // Java ki List ko reverse karne ke liye taake user ko LIFO order mile
        java.util.Collections.reverse(historyList);
        return historyList;
    }

    public boolean hasHistory() {
        return !historyStack.isEmpty();
    }
}
