import java.util.UUID;
import java.util.ArrayList;

public class PhysicalVolume{
    private UUID PVUUID;
    private String name;
    private PhysicalHDD hdd;
    private boolean c;
    private static final ArrayList<PhysicalHDD> existingDrives = new ArrayList<>();
    private static final ArrayList<String> pVNames = new ArrayList<>();

    public PhysicalVolume(String name, PhysicalHDD drive) {
        if (drive == null) {
            System.out.println("Error: Hard-drive doesn't exist");
            c = true;
        }
        else {
            for (PhysicalHDD drives : existingDrives) {
                if (drive.getName().equals(drives.getName())) {
                    System.out.println("Error: This hard-drive is attached to another Physical Volume");
                    c = true;
                }
            }
        }
        if (!c) {
            System.out.println("Physical Volume " + name + " has been installed");
            hdd = drive;
            this.name = name;
            existingDrives.add(hdd);
            pVNames.add(name);
            PVUUID = UUID.randomUUID();
        }
    }

    public static ArrayList<String> getpVNames() { return pVNames; }

    public UUID getPVUUID() {
        return PVUUID;
    }

    public PhysicalHDD getHDD() {
        return hdd;
    }

    public boolean hasNoErrors() { return c; }

    public String getName() {
        return name;
    }

}
