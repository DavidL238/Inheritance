import java.util.UUID;

public class PhysicalVolume{
    private final UUID PVUUID;
    private String name;
    private PhysicalHDD hdd;

    public PhysicalVolume(String name, PhysicalHDD hdd) {
        this.name = name;
        this.hdd = hdd;
        PVUUID = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

}
