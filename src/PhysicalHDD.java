import java.util.ArrayList;

public class PhysicalHDD {
    private final String name, storage;
    private static final ArrayList<String> hddNames = new ArrayList<>();
    private static final ArrayList<String> hddSizes = new ArrayList<>();

    public PhysicalHDD (String name, String storage) {
        this.name = name;
        this.storage = storage;
        hddNames.add(name);
        hddSizes.add(storage);
        System.out.println("Drive " + name + " has been installed.");
    }

    public static ArrayList<String> getHDDNames() {
        return hddNames;
    }

    public static ArrayList<String> getHDDSizes() {
        return hddSizes;
    }

    public String getName() {return name;}

    public String getStorage() {return storage;}
}
