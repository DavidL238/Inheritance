import java.util.ArrayList;

public class VolumeGroup {
    private static ArrayList<PhysicalVolume> volumes = new ArrayList<>();
    private static ArrayList<PhysicalVolume> inUse = new ArrayList<>();
    private String volumeName;

    public VolumeGroup(String volumeName, PhysicalVolume pVolume) {
        boolean c = checkUse(pVolume);
        if (!c) {
            this.volumeName = volumeName;
            inUse.add(pVolume);
            volumes.add(pVolume);
        }
    }

    public void extend(PhysicalVolume v) {
        boolean c = checkUse(v);
        if (!c) {
            volumes.add(v);
            inUse.add(v);
        }
    }

    public void listAll() {
        for (PhysicalVolume v : inUse) {
            System.out.println(v.getName());
        }
    }

    public boolean checkUse(PhysicalVolume volume) {
        for (PhysicalVolume v : volumes) {
            if (v.getName().equals(volume.getName())) {
                System.out.println("Error: Physical Volume in Use");
                return true;
            }
        }
        return false;
    }


}
