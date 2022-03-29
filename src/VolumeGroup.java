import java.util.ArrayList;
import java.util.UUID;

public class VolumeGroup extends Installer{
    private static ArrayList<PhysicalVolume> physicalVolumes = new ArrayList<>();
    private ArrayList<PhysicalVolume> inUse = new ArrayList<>();
    private ArrayList<LogicalVolume> LVs = new ArrayList<>();
    private int totalStorage, freeStorage;

    public VolumeGroup(String name, PhysicalVolume pVolume) {
        super(name, pVolume.getStorage(), UUID.randomUUID());
        if (!checkUse(pVolume)) {
            inUse.add(pVolume);
            physicalVolumes.add(pVolume);
            calculateTotal();
            freeStorage = totalStorage;
        }
    }

    public void extend(PhysicalVolume v) {
        boolean c = checkUse(v);
        if (!c) {
            physicalVolumes.add(v);
            inUse.add(v);
            int original = totalStorage;
            calculateTotal();
            freeStorage += totalStorage - original;
            super.extend(totalStorage);
        }
    }

    public boolean addLV(LogicalVolume lv) {
        if (lv.getStorageNum() <= freeStorage) {
            LVs.add(lv);
            freeStorage -= lv.getStorageNum();
            return true;
        }
        else {
            return false;
        }
    }

    public void reduce(int amt) {
        freeStorage -= amt;
    }

    public static boolean checkUse(PhysicalVolume volume) {
        for (PhysicalVolume v : physicalVolumes) {
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
            totalStorage += calculateStorage(v.getHDD().getStorage());
        }
    }

    public boolean hasPV(PhysicalVolume a) {
        for (PhysicalVolume pv : inUse) {
            if (a.getName().equals(pv.getName())) {
                return true;
            }
        }
        return false;
    }

    public String allAssociates() {
        String r = "";
        for (PhysicalVolume pv : inUse) {
            r = r + pv.getName() + ", ";
        }
        r = r.substring(0, r.length() - 2);
        return r;
    }

    public int getFreeStorage() {
        return freeStorage;
    }

}
