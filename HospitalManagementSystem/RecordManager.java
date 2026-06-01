package HospitalManagementSystem;
import java.util.ArrayList;
import java.util.List;

// This class is for to store Patient Data permanently. Sorting and Searching based on patient ID.(BST)

public class RecordManager {
    private class Node{
        Patient patient;
        Node left, right;

        public Node(Patient patient){
            this.patient = patient;
            left = right = null;
        }
    }

    private Node root;
    public RecordManager(){
        root = null;
    }

    public void addPatientRecord(Patient p){
        root = insertRec(root, p);
        System.out.println("Record Saved: " + p.getName() + " (ID: " + p.getId() + ")");
    }

    private Node insertRec(Node root, Patient p){
        if(root == null){
            root = new Node(p);
            return root;
        }
        if(p.getId() < root.patient.getId()){
            root.left = insertRec(root.left,p);
        }
        else if (p.getId() > root.patient.getId()) {
            root.right = insertRec(root.right,p);
        }

        return root;
    }

    public Patient searchPatient(int id){
        Node result= searchRec(root, id);
        if (result != null) {
            return result.patient;
        } else {
            System.out.println("Error: Patient ID " + id + " Not Found");
            return null;
        }

    }

    private Node searchRec(Node root, int id ){
        if(root == null || root.patient.getId() == id){
            return root;
        }
        if (root.patient.getId() > id) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }

    // Get all Patient data,sort it and display is ascending order (inorder)

    public List<Patient> getAllPatients(){
        List<Patient> patientList = new ArrayList<>();
        inOrderRec(root, patientList);
        return patientList;
    }

    // Inorder Treversal
    private void inOrderRec(Node root, List<Patient> list){
        if(root != null){
            inOrderRec(root.left,list);
            list.add(root.patient);
            inOrderRec(root.right,list);
        }
    }
}
