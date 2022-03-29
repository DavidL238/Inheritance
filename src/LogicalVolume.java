import java.util.UUID;

public class LogicalVolume extends Installer{
    private VolumeGroup vg = null;

    public LogicalVolume(String name, String size, VolumeGroup vg) {
        super(name, size, UUID.randomUUID());
        int storageSize = getStorageNum();
        if (vg.getFreeStorage() >= storageSize) {
            this.vg = vg;
        }
        else {
            System.out.println("Error: Insufficient Storage Space");
        }
    }

    public void extend(String amt) {
        int addedStorage = calculateStorage(amt);
        if (vg.getFreeStorage() - addedStorage < 0) {
            System.out.println("Error: Insufficient Storage Space");
        }
        else {
            System.out.println("Success: LG " + getName() + " Extended By " + addedStorage + "G");
            super.extend(addedStorage + getStorageNum());
            vg.reduce(addedStorage);
        }
    }

    public VolumeGroup getVG() {
        return vg;
    }
}
