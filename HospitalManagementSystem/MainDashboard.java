package HospitalManagementSystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainDashboard extends JFrame {
    // 1. Managers Initialization (Linked with your classes)
    private ERManager erManager = new ERManager();
    private RecordManager recordManager = new RecordManager();
    private HistoryManager historyManager = new HistoryManager();
    private AppointmentManager appointmentManager = new AppointmentManager();
    private WardManager wardManager = new WardManager(3, 5);

    // Colors for Professional Look (Mac & Windows Compatible)
    private final Color PRIMARY_BLUE = new Color(41, 128, 185);
    private final Color SUCCESS_GREEN = new Color(39, 174, 96);
    private final Color DANGER_RED = new Color(192, 57, 43);
    private final Color DARK_NAVY = new Color(44, 62, 80);

    private JTextArea logArea;

    public MainDashboard() {
        // Mac par colors show karne ke liye Cross-Platform LookAndFeel
        try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}

        setTitle("Smart Hospital Management System - DSA Project");
        setSize(1000, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- Header Section ---
        JPanel header = new JPanel();
        header.setBackground(DARK_NAVY);
        header.setPreferredSize(new Dimension(0, 70));
        JLabel lblTitle = new JLabel("HOSPITAL MANAGEMENT DASHBOARD");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        header.add(lblTitle);
        add(header, BorderLayout.NORTH);

        // --- Tabs Section ---
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Arial", Font.BOLD, 13));
        tabs.addTab("Emergency (Heap)", createERPanel());
        tabs.addTab("Appointments (LinkedList)", createAppointmentPanel());
        tabs.addTab("Records (BST)", createRecordPanel());
        tabs.addTab("History (Stack)", createHistoryPanel());
        tabs.addTab("Wards (2D Array)", createWardPanel());
        add(tabs, BorderLayout.CENTER);

        // --- System Logs (The Green Console) ---
        logArea = new JTextArea(12, 50);
        logArea.setBackground(new Color(20, 20, 20));
        logArea.setForeground(new Color(0, 255, 60));
        logArea.setFont(new Font("Monospaced", Font.BOLD, 13));
        logArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(logArea);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(PRIMARY_BLUE), "SYSTEM ACTIVITY LOGS", TitledBorder.LEFT, TitledBorder.TOP, null, PRIMARY_BLUE));
        add(scroll, BorderLayout.SOUTH);
    }

    // Helper: Styled Button
    private JButton createStyledBtn(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    // 1. ER Panel: Using erManager.admitToER & treatNextPatient
    private JPanel createERPanel() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(new EmptyBorder(30, 150, 30, 150));
        panel.setBackground(Color.WHITE);

        JTextField tID = new JTextField(); JTextField tName = new JTextField();
        JTextField tSev = new JTextField(); JTextField tCond = new JTextField();

        panel.add(new JLabel("Patient ID:")); panel.add(tID);
        panel.add(new JLabel("Name:")); panel.add(tName);
        panel.add(new JLabel("Severity (1-5):")); panel.add(tSev);
        panel.add(new JLabel("Condition:")); panel.add(tCond);

        JButton btnAdmit = createStyledBtn("ADMIT TO ER", SUCCESS_GREEN);
        JButton btnTreat = createStyledBtn("TREAT NEXT", PRIMARY_BLUE);

        btnAdmit.addActionListener(e -> {
            try {
                Patient p = new Patient(Integer.parseInt(tID.getText()), tName.getText(), Integer.parseInt(tSev.getText()), tCond.getText(), "N/A");
                erManager.admitToER(p);
                recordManager.addPatientRecord(p); // Saving to BST automatically
                updateLog("ADMITTED: " + p.getName() + " (Priority: " + p.getSeverity() + ")");
                tID.setText(""); tName.setText(""); tSev.setText(""); tCond.setText("");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Please check Numeric Fields!"); }
        });

        btnTreat.addActionListener(e -> {
            Patient p = erManager.treatNextPatient();
            if(p != null) {
                historyManager.addVisitToHistory(p); // Move to Stack
                updateLog("TREATING: " + p.getName() + " - Moved to History Stack.");
            }
        });

        panel.add(btnAdmit); panel.add(btnTreat);
        return panel;
    }

    // 2. Appointment Panel: Using schedule, cancel, serveNext, getWaitingCount
    private JPanel createAppointmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(20, 50, 20, 50));
        panel.setBackground(Color.WHITE);

        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createTitledBorder("New Appointment Form"));
        JTextField tID = new JTextField(); JTextField tName = new JTextField(); JTextField tCont = new JTextField();
        form.add(new JLabel("Patient ID:")); form.add(tID);
        form.add(new JLabel("Full Name:")); form.add(tName);
        form.add(new JLabel("Contact:")); form.add(tCont);
        JButton btnBook = createStyledBtn("BOOK APPOINTMENT", PRIMARY_BLUE);
        form.add(new JLabel("Action:")); form.add(btnBook);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton btnNext = createStyledBtn("SERVE NEXT", SUCCESS_GREEN);
        JButton btnCount = createStyledBtn("CHECK COUNT", DARK_NAVY);
        JTextField tCancel = new JTextField(5);
        JButton btnCancel = createStyledBtn("CANCEL ID", DANGER_RED);

        actions.add(btnNext); actions.add(btnCount);
        actions.add(new JLabel("ID to Cancel:")); actions.add(tCancel); actions.add(btnCancel);

        panel.add(form, BorderLayout.NORTH);
        panel.add(actions, BorderLayout.CENTER);

        // Listeners for AppointmentManager
        btnBook.addActionListener(e -> {
            try {
                Patient p = new Patient(Integer.parseInt(tID.getText()), tName.getText(), 5, "Checkup", tCont.getText());
                appointmentManager.scheduleAppointment(p);
                recordManager.addPatientRecord(p); // Linked to Search
                updateLog("BOOKED: " + p.getName());
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "ID error!"); }
        });

        btnNext.addActionListener(e -> {
            Patient p = appointmentManager.serveNextAppointment();
            if(p != null) {
                historyManager.addVisitToHistory(p);
                updateLog("SERVED: Appointment for " + p.getName() + " completed.");
            }
        });

        btnCount.addActionListener(e -> updateLog("CURRENT QUEUE: " + appointmentManager.getWaitingCount() + " patients."));

        btnCancel.addActionListener(e -> {
            if(appointmentManager.cancelAppointment(Integer.parseInt(tCancel.getText())))
                updateLog("CANCELLED: Appointment for ID " + tCancel.getText());
            else updateLog("ERROR: ID not found in appointments.");
        });

        return panel;
    }

    // 3. Records Panel: Using searchPatient
    private JPanel createRecordPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 100));
        panel.setBackground(Color.WHITE);
        JTextField tSearch = new JTextField(15);
        JButton btn = createStyledBtn("SEARCH IN BST", DARK_NAVY);
        btn.addActionListener(e -> {
            Patient p = recordManager.searchPatient(Integer.parseInt(tSearch.getText()));
            if(p != null) updateLog("FOUND: " + p.toString());
            else updateLog("NOT FOUND: ID " + tSearch.getText() + " does not exist.");
        });
        panel.add(new JLabel("Enter Patient ID to Search:")); panel.add(tSearch); panel.add(btn);
        return panel;
    }

    // 4. History Panel: Using getFullHistory (Stack)
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 100));
        panel.setBackground(Color.WHITE);
        JButton btn = createStyledBtn("SHOW RECENT TREATMENT HISTORY", SUCCESS_GREEN);
        btn.setPreferredSize(new Dimension(300, 50));
        btn.addActionListener(e -> {
            updateLog("--- HISTORY STACK (LIFO) ---");
            historyManager.getFullHistory().forEach(p -> updateLog(p.toString()));
        });
        panel.add(btn);
        return panel;
    }

    // 5. Ward Panel: Using assignBed & showWardStatus
    private JPanel createWardPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBorder(new EmptyBorder(50, 150, 50, 150));
        panel.setBackground(Color.WHITE);
        JTextField tF = new JTextField(); JTextField tB = new JTextField(); JTextField tN = new JTextField();
        JButton btnAssign = createStyledBtn("ASSIGN BED", SUCCESS_GREEN);
        JButton btnStatus = createStyledBtn("CONSOLE STATUS", DARK_NAVY);

        btnAssign.addActionListener(e -> {
            Patient p = new Patient(0, tN.getText(), 0, "Ward Stay", "");
            if(wardManager.assignBed(Integer.parseInt(tF.getText()), Integer.parseInt(tB.getText()), p))
                updateLog("WARD: Floor " + tF.getText() + " Bed " + tB.getText() + " allocated to " + tN.getText());
        });

        btnStatus.addActionListener(e -> {
            wardManager.showWardStatus();
            updateLog("CHECK SYSTEM CONSOLE (IntelliJ Output) for 2D Grid view.");
        });

        panel.add(new JLabel("Floor (0-2):")); panel.add(tF);
        panel.add(new JLabel("Bed (0-4):")); panel.add(tB);
        panel.add(new JLabel("Patient Name:")); panel.add(tN);
        panel.add(btnAssign); panel.add(btnStatus);
        return panel;
    }

    private void updateLog(String msg) { logArea.append(" > " + msg + "\n"); }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainDashboard().setVisible(true));
    }
}