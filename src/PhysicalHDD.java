import java.util.ArrayList;

public class PhysicalHDD {
    private final String name;
    private final int storage;
    private static final ArrayList<String> hddNames = new ArrayList<>();
    private static final ArrayList<Integer> hddSizes = new ArrayList<>();

    public PhysicalHDD (String name, int storage) {
        this.name = name;
        this.storage = storage;
        hddNames.add(name);
        hddSizes.add(storage);
    }

    public static ArrayList<String> getHDDNames() {
        return hddNames;
    }

    public ArrayList<Integer> getHDDSizes() {
        return hddSizes;
    }

    public String getName() {return name;}

    public int getStorage() {return storage;}
}
