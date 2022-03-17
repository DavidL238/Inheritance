import java.util.UUID;
import java.util.ArrayList;

public class PhysicalVolume{
    private final UUID PVUUID;
    private String name;
    private PhysicalHDD hdd;
    private static final ArrayList<PhysicalHDD> existingDrives = new ArrayList<>();

    public PhysicalVolume(String name, PhysicalHDD hdd) {
        this.hdd = hdd;
        this.name = name;
        existingDrives.add(hdd);
        PVUUID = UUID.randomUUID();
    }

    public UUID getPVUUID() {
        return PVUUID;
    }

    public PhysicalHDD getHDD() {
        return hdd;
    }

    public String getName() {
        return name;
    }

}
