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
        int total = 0;
        for (PhysicalVolume v : inUse) {
            String str = v.getHDD().getStorage();
            for (int i = 0; i < str.length(); i++) {
                String sub = str.substring(i, i+1);
                try {
                    total += Integer.parseInt(sub);
                }
                catch (NumberFormatException ignored) {}
            }
        }
        for (PhysicalVolume v : inUse) {
            System.out.println(v.getName() + ": total: [" + total + "] ");
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
