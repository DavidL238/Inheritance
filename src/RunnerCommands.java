import java.util.ArrayList;

public class RunnerCommands {
    private ArrayList<PhysicalHDD> driveList = new ArrayList<>();
    private ArrayList<PhysicalVolume> physicalVolumes = new ArrayList<>();

    public void installDrive(String response) {
        int idx = response.indexOf(" ");
        if (hasFewArgs(idx)) {
            String name = response.substring(idx+1);
            idx = name.indexOf(" ");
            String size = name.substring(idx+1);
            name = name.substring(0, idx);

            ArrayList<String> existingNames = PhysicalHDD.getHDDNames();
            boolean isAvailable = true;
            for (String names : existingNames) {
                if (names.equals(name)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                PhysicalHDD newDrive = new PhysicalHDD(name, size);
                driveList.add(newDrive);
            }
            else {
                System.out.println("Another Hard-drive is already assigned to this name!");
            }
        }
    }

    public void listDrives() {
        for (PhysicalHDD drives : driveList) {
            System.out.println(drives.getName() + " [" + drives.getStorage() + "]");
        }
    }

    public void createPhysicalVolume(String response) {
        int idx = response.indexOf(" ");
        if (hasFewArgs(idx)) {
            String name = response.substring(idx + 1);
            idx = name.indexOf(" ");
            String driveName = name.substring(idx + 1);
            name = name.substring(0, idx);

            ArrayList<String> existingNames = PhysicalVolume.getpVNames();
            boolean isAvailable = true;
            for (String names : existingNames) {
                if (names.equals(name)) {
                    isAvailable = false;
                    break;
                }
            }
            if (isAvailable) {
                PhysicalHDD addDrive = null;
                for (PhysicalHDD drives : driveList) {
                    if (drives.getName().equals(driveName)) {
                        addDrive = drives;
                    }
                }
                PhysicalVolume newVolume = new PhysicalVolume(name, addDrive);
                if (!newVolume.hasNoErrors()) {
                    physicalVolumes.add(newVolume);
                }
            } else {
                System.out.println("Another Physical Volume is already assigned to this name!");
            }
        }
    }

    private boolean hasFewArgs(int idx) {
        if (idx == -1) {
            System.out.println("Error: Command has too few arguments");
            return false;
        }
        return true;
    }
}
