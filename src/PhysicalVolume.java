import java.util.UUID;
import java.util.ArrayList;

public class PhysicalVolume extends Installer{
    private PhysicalHDD hdd;
    private VolumeGroup vg;
    private static final ArrayList<PhysicalHDD> existingDrives = new ArrayList<>();

    public PhysicalVolume(String name, PhysicalHDD drive) {
        super("thisisatemporaryphysicalvolumepleasedisregardthislongstringliteral", 0, UUID.randomUUID());
        boolean c = false;
        for (PhysicalHDD drives : existingDrives) {
            if (drive.getName().equals(drives.getName())) {
                System.out.println("Error: This hard-drive is attached to another Physical Volume");
                c = true;
            }
        }
        if (!c) {
            setName(name);
            setStorage(drive.getStorage());
            hdd = drive;
            existingDrives.add(hdd);
        }
    }

    public PhysicalVolume (String name, PhysicalHDD drive, UUID uuid) {
        super(name, drive.getStorage(), uuid);
        hdd = drive;
        existingDrives.add(hdd);
    }

    public PhysicalHDD getHDD() {
        return hdd;
    }

    public String getVGName() {
        return vg.getName();
    }

    public void setVG(VolumeGroup a) {
        vg = a;
    }
}
