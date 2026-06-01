package HospitalManagementSystem;

// 2D array will use.
public class WardManager {
    private Patient[][] wardLayout;
    private final int totalFloors;
    private final int bedsPerFloor;

    public WardManager(int floors, int beds) {
        this.totalFloors = floors;
        this.bedsPerFloor = beds;
        this.wardLayout = new Patient[floors][beds];
    }

    // assign bed to Patient on specific position.
    public boolean assignBed(int floor, int bed, Patient p) {
        if (isValidPosition(floor, bed)) {
            if (wardLayout[floor][bed] == null) {
                wardLayout[floor][bed] = p;
                System.out.println("Bed Assigned: " + p.getName() + " on Floor " + floor + ", Bed " + bed);
                return true;
            } else {
                System.out.println("Error: Bed " + bed + " Floor " + floor + " occupied.");
            }
        }
        return false;
    }

    public void dischargeFromBed(int floor, int bed) {
        if (isValidPosition(floor, bed) && wardLayout[floor][bed] != null) {
            System.out.println("Discharge: " + wardLayout[floor][bed].getName());
            wardLayout[floor][bed] = null;
        }
    }

    public void showWardStatus() {
        System.out.println("\n--- Current Ward Status (Occupancy) ---");
        for (int i = 0; i < totalFloors; i++) {
            System.out.print("Floor " + i + ": ");
            for (int j = 0; j < bedsPerFloor; j++) {
                if (wardLayout[i][j] == null) {
                    System.out.print("[ Empty ] ");
                } else {
                    System.out.print("[" + wardLayout[i][j].getName() + "] ");
                }
            }
            System.out.println();
        }
    }

    // for checking
    private boolean isValidPosition(int floor, int bed) {
        return (floor >= 0 && floor < totalFloors && bed >= 0 && bed < bedsPerFloor);
    }

    // for GUI we need complete list
    public Patient[][] getWardLayout() {
        return wardLayout;
    }
}
