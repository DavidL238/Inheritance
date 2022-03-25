import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;

public class VolumeGroup {
    private static ArrayList<PhysicalVolume> volumes = new ArrayList<>();
    private static ArrayList<PhysicalVolume> inUse = new ArrayList<>();
    private static ArrayList<LogicalVolume> LVs = new ArrayList<>();
    private String volumeName;
    private int totalStorage, freeStorage;
    private UUID uuid;

    public VolumeGroup(String volumeName, PhysicalVolume pVolume) {
        boolean c = checkUse(pVolume);
        if (!c) {
            this.volumeName = volumeName;
            inUse.add(pVolume);
            volumes.add(pVolume);
            uuid = UUID.randomUUID();
            calculateTotal();
            freeStorage = totalStorage;
        }
    }

    public void extend(PhysicalVolume v) {
        boolean c = checkUse(v);
        if (!c) {
            volumes.add(v);
            inUse.add(v);
            calculateTotal();
        }
    }

    public void listAll() {
        int total = 0;
        int test = 0;
    }

    public void reduceFreeStorage(int amt) {
        freeStorage -= amt;
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

    private void calculateTotal() {
        totalStorage = 0;
        for (PhysicalVolume v : inUse) {
            totalStorage += calculateStorage(v);
        }
    }

    private int calculateStorage(PhysicalVolume input) {
        String str = input.getHDD().getStorage();
        int test = 0;
        for (int i = str.length() - 1; i > 0; i--) {
            String sub = str.substring(0, i);
            try {
                test += Integer.parseInt(sub);
                if (test > 0) {
                    break;
                }
            }
            catch (NumberFormatException ignored) {}
        }
        return test;
    }

    public static ArrayList<PhysicalVolume> getVolumes() {
        return volumes;
    }

    public String getVolumeName() {
        return volumeName;
    }

    public int getFreeStorage() {
        return freeStorage;
    }

    public void addLV(LogicalVolume lv) {
        LVs.add(lv);
    }


}
