import java.util.UUID;

public class Installer {
    private String name;
    private String storage;
    private int storageNum;
    private final UUID uuid;

    public Installer (String name, String storage, UUID uuid) {
        this.name = name;
        this.storage = storage;
        if (!storage.contains("G") || !storage.contains("g")) {
            this.storage = storage + "G";
        }
        storageNum = calculateStorage(storage);
        this.uuid = uuid;
    }

    public Installer (String name, int storageNum, UUID uuid) {
        this.name = name;
        storage = storageNum + "G";
        this.storageNum = storageNum;
        this.uuid = uuid;
    }

    public int calculateStorage(String storage) {
        int test = 0;
        try {
            test += Integer.parseInt(storage);
            if (test > 0) {
                return test;
            }
        }
        catch (NumberFormatException ignored) {}
        for (int i = storage.length() - 1; i > 0; i--) {
            String sub = storage.substring(0, i);
            try {
                test += Integer.parseInt(sub);
                if (test > 0) {
                    break;
                }
            }
            catch (NumberFormatException ignored) {}
        }
        return test;
    }

    public void extend (int totalStorage){
        storageNum = totalStorage;
        storage = storageNum + "G";
    }

    public String getName() {
        return name;
    }

    public String getStorage() {
        return storage;
    }

    public int getStorageNum() {
        return storageNum;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStorage(String storage) {
        this.storage = storage;
        storageNum = calculateStorage(storage);
    }
}
