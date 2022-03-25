import java.util.ArrayList;

public class RunnerCommands {
    private ArrayList<PhysicalHDD> driveList = new ArrayList<>();
    private ArrayList<PhysicalVolume> physicalVolumes = new ArrayList<>();
    private ArrayList<VolumeGroup> volumeGroups = new ArrayList<>();

    public void installDrive(String response) {
        int idx = response.indexOf(" ");
        if (!hasTooFewArgs(idx)) {
            String[] info = seperateInfo(idx, response);
            String name = info[0];
            String size = info[1];

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
                System.out.println("Error: HDD Name in Use");
            }
        }
    }

    // Prints all drives in driveList
    public void listDrives() {
        for (PhysicalHDD drives : driveList) {
            System.out.println(drives.getName() + " [" + drives.getStorage() + "]");
        }
    }

    public void createPhysicalVolume(String response) {
        int idx = response.indexOf(" ");
        if (!hasTooFewArgs(idx)) {
            String[] info = seperateInfo(idx, response);
            String name = info[0];
            String driveName = info[1];

            ArrayList<String> existingNames = PhysicalVolume.getpVNames();
            boolean isAvailable = true;
            for (String names : existingNames) {
                if (names.equals(name)) {
                    isAvailable = false;
                    System.out.println("Error: PV Name in Use");
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
            }
        }
    }

    public void listPV() {
        System.out.println();
    }

    public void createVolumeGroup(String response) {
        int idx = response.indexOf(" ");
        if (!hasTooFewArgs(idx)) {
            String[] info = seperateInfo(idx, response);
            String name = info[0];
            String pvName = info[1];

            boolean c = true;
            for (VolumeGroup vg : volumeGroups) {
                if (vg.getVolumeName().equals(name)) {
                    c = false;
                    break;
                }
            }

            PhysicalVolume temp = null;
            boolean f = false;
            for (PhysicalVolume pv : physicalVolumes) {
                if (pv.getName().equals(pvName)) {
                    temp = pv;
                    f = true;
                }
            }

            if (c && f) {
                VolumeGroup newVG = new VolumeGroup(name, temp);
                volumeGroups.add(newVG);
                System.out.println("VG created :)");
            }
            else if (c) {
                System.out.println("Error: PV not found");
            }
            else {
                System.out.println("Error: VG Name in Use");
            }
        }
    }

    public void extendVG(String response) {
        int idx = response.indexOf(" ");
        if (!hasTooFewArgs(idx)) {
            String[] info = seperateInfo(idx, response);
            String name = info[0];
            String pvName = info[1];

            VolumeGroup a = null;
            for (VolumeGroup vg : volumeGroups) {
                if (vg.getVolumeName().equals(name)) {
                    a = vg;
                }
            }

            PhysicalVolume b = null;
            for (PhysicalVolume pv : physicalVolumes) {
                if (pv.getName().equals(pvName)) {
                    b = pv;
                }
            }
            if (a != null && b != null) {
                a.extend(b);
                System.out.println("Success: " + pvName + " is extended to " + name);
            }
            else if (a != null) {
                System.out.println("Error: PV doesn't exist");
            }
            else {
                System.out.println("Error: VG doesn't exist");
            }
        }
    }

    public void listVG() {

    }

    // Returns false if idx > -1
    private boolean hasTooFewArgs(int idx) {
        if (idx == -1) {
            System.out.println("Error: Command has too few arguments");
            return true;
        }
        return false;
    }

    private String[] seperateInfo(int idx, String response) {
        String name = response.substring(idx + 1);
        idx = name.indexOf(" ");
        String arg2 = name.substring(idx + 1);
        name = name.substring(0, idx);
        String[] out = new String[2];
        out[0] = name;
        out[1] = arg2;
        return out;
    }
}
