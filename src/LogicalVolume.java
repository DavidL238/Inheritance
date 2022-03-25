import java.util.ArrayList;
import java.util.UUID;

public class LogicalVolume {
    private String name;
    private int size;
    private VolumeGroup vg;
    private UUID uuid;

    public LogicalVolume(String name, int size, VolumeGroup vg) {
        if (vg.getFreeStorage() >= size) {
            this.name = name;
            this.size = size;
            this.vg = vg;
            uuid = UUID.randomUUID();
        }
        else {
            System.out.println("Error: Free VG storage is insufficient");
        }
    }
}
