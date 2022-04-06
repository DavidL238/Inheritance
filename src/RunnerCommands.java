import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class RunnerCommands {
    private ArrayList<PhysicalHDD> driveList = new ArrayList<>();
    private ArrayList<PhysicalVolume> physicalVolumes = new ArrayList<>();
    private ArrayList<VolumeGroup> volumeGroups = new ArrayList<>();
    private ArrayList<LogicalVolume> logicalVolumes = new ArrayList<>();

    public void save() throws IOException {
        BufferedWriter saveHDD = new BufferedWriter(new FileWriter("src//Save//PhysicalHDDs.txt"));
        BufferedWriter savePV = new BufferedWriter(new FileWriter("src//Save//PhysicalVolumes.txt"));
        BufferedWriter saveVG = new BufferedWriter(new FileWriter("src//Save//VolumeGroups.txt"));
        BufferedWriter saveLV = new BufferedWriter(new FileWriter("src//Save//LogicalVolumes.txt"));

        for (PhysicalHDD hdds : driveList) {
            saveHDD.write(hdds.getName() + "|" + hdds.getStorageNum());
            saveHDD.newLine();
        }
        for (PhysicalVolume pvs : physicalVolumes) {
            savePV.write(pvs.getName() + "|" + pvs.getHDD().getName());
            savePV.newLine();
        }
        for (VolumeGroup vgs : volumeGroups) {
            saveVG.write(vgs.getName());
            for (PhysicalVolume pvs : physicalVolumes) {
                if (vgs.hasPV(pvs)) {
                    saveVG.write("|" + pvs.getName());
                }
            }
            saveVG.newLine();
        }
        for (LogicalVolume lvs : logicalVolumes) {
            saveLV.write(lvs.getName() + "|" + lvs.getStorageNum() + "|" + lvs.getVG().getName());
            saveLV.newLine();
        }
        saveHDD.flush();
        saveHDD.close();
        savePV.flush();
        savePV.close();
        saveVG.flush();
        saveVG.close();
        saveLV.flush();
        saveLV.close();
    }

    public void load() throws FileNotFoundException {
        loadDrives();
        loadPhysicalVolumes();
        loadVolumeGroups();
        loadLogicalVolumes();
        System.out.println(
                "Microsoft Windows [Version 10.0.19043.1586]\n" +
                "(c) Microsoft Corporation. All rights reserved.");
    }

    public void loadDrives() throws FileNotFoundException {
        File file = new File("src//Save//PhysicalHDDs.txt");
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] params = line.split("\\|");
            PhysicalHDD loadDrive = new PhysicalHDD(params[0], params[1]);
            driveList.add(loadDrive);
        }
    }

    public void loadPhysicalVolumes() throws FileNotFoundException {
        File file = new File("src//Save//PhysicalVolumes.txt");
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] params = line.split("\\|");
            PhysicalHDD temp = null;
            for (PhysicalHDD drives : driveList) {
                if (drives.getName().equals(params[1])) {
                    temp = drives;
                }
            }
            PhysicalVolume loadVolume = new PhysicalVolume(params[0], temp);
            physicalVolumes.add(loadVolume);
        }
    }

    public void loadVolumeGroups() throws FileNotFoundException {
        File file = new File("src//Save//VolumeGroups.txt");
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] params = line.split("\\|");
            ArrayList<PhysicalVolume> otherExtends = new ArrayList<>();
            for (PhysicalVolume pvs : physicalVolumes) {
                for (int i = 1; i < params.length; i++) {
                    if (pvs.getName().equals(params[i])) {
                        otherExtends.add(pvs);
                    }
                }
            }
            VolumeGroup loadVolume = new VolumeGroup(params[0], otherExtends.get(0));
            for (PhysicalVolume pvs : otherExtends) {
                loadVolume.extend(pvs);
                pvs.setVG(loadVolume);
            }
            volumeGroups.add(loadVolume);
        }
    }

    public void loadLogicalVolumes() throws FileNotFoundException {
        File file = new File("src//Save//LogicalVolumes.txt");
        Scanner s = new Scanner(file);
        while (s.hasNextLine()) {
            String line = s.nextLine();
            String[] params = line.split("\\|");
            VolumeGroup temp = null;
            for (VolumeGroup vgs : volumeGroups) {
                if (vgs.getName().equals(params[2])) {
                    temp = vgs;
                }
            }
            LogicalVolume loadLogicalVolume = new LogicalVolume(params[0], params[1], temp);
            logicalVolumes.add(loadLogicalVolume);
            temp.addLV(loadLogicalVolume);
        }
    }

    public void installDrive(String response) {
        int idx = response.indexOf(" ");
        if (checkArgs(idx, response)) {
            String[] info = separateInfo(response);
            String name = info[0];
            String size = info[1];
            boolean isAvailable = true;
            for (PhysicalHDD drives : driveList) {
                if (drives.getName().equals(name)) {
                    isAvailable = false;
                    System.out.println("Error: HDD Name In Use");
                    break;
                }
            }
            if (isAvailable) {
                PhysicalHDD newDrive = new PhysicalHDD(name, size);
                driveList.add(newDrive);
                System.out.println("Success: HDD Installed");
            }
        }
    }

    // Prints all drives in driveList
    public void listDrives() {
        for (PhysicalHDD drives : driveList) {
            System.out.println(drives.getName() + " [" + drives.getStorage() + "]");
        }
    }

    public void createPhysicalVolume(String response) {
        int idx = response.indexOf(" ");
        if (checkArgs(idx, response)) {
            String[] info = separateInfo(response);
            String name = info[0];
            String driveName = info[1];
            boolean isAvailable = true;
            for (PhysicalVolume pvs : physicalVolumes) {
                if (pvs.getName().equals(name)) {
                    isAvailable = false;
                    System.out.println("Error: PV Name In Use");
                    break;
                }
            }
            if (isAvailable) {
                PhysicalHDD addDrive = null;
                for (PhysicalHDD drives : driveList) {
                    if (drives.getName().equals(driveName)) {
                        addDrive = drives;
                    }
                }
                if (addDrive == null) {
                    System.out.println("Error: Hard-drive doesn't exist");
                }
                else {
                    PhysicalVolume newVolume = new PhysicalVolume(name, addDrive);
                    if (newVolume.getHDD() != null) {
                        physicalVolumes.add(newVolume);
                        System.out.println("Success: Physical Volume Created");
                    }
                }
            }
        }
    }

    // Why does this work? I dunno.
    public void listPV() {
        ArrayList<PhysicalVolume> hasVG = new ArrayList<>();
        ArrayList<PhysicalVolume> noVG = new ArrayList<>();
        for (PhysicalVolume physicalVolume : physicalVolumes) {
            if (VolumeGroup.checkUse(physicalVolume)) {
                hasVG.add(physicalVolume);
            } else {
                noVG.add(physicalVolume);
            }
        }
        for (int i = 0; i < hasVG.size() - 2; i++) {
            VolumeGroup a = null;
            VolumeGroup b = null;
            for (VolumeGroup volumeGroup : volumeGroups) {
                if (volumeGroup.hasPV(hasVG.get(i))) {
                    a = volumeGroup;
                }
                if (volumeGroup.hasPV(hasVG.get(i + 1))) {
                    b = volumeGroup;
                }
                boolean t = true;
                if (a == null) {
                    noVG.add(hasVG.remove(i));
                    t = false;
                }
                if (b == null) {
                    noVG.add(hasVG.remove(i + 1));
                    t = false;
                }
                if (t && a.getName().compareTo(b.getName()) > 0) {
                    PhysicalVolume temp = hasVG.get(i + 1);
                    hasVG.set(i + 1, hasVG.get(i));
                    hasVG.set(i, temp);
                    i--;
                }
            }
        }
        for (int i = 0; i < hasVG.size() - 1; i++) {
            String vgOne = hasVG.get(i).getVGName();
            String vgTwo = hasVG.get(i+1).getVGName();
            String pvOne = hasVG.get(i).getName();
            String pvTwo = hasVG.get(i+1).getName();
            PhysicalVolume temp = hasVG.get(i+1);
            while (pvOne.compareTo(pvTwo) > 0 && vgOne.equals(vgTwo)) {
                hasVG.set(i + 1, hasVG.get(i));
                hasVG.set(i, temp);
                i--;
                pvOne = hasVG.get(i).getName();
                pvTwo = hasVG.get(i+1).getName();
                vgOne = hasVG.get(i).getVGName();
                vgTwo = hasVG.get(i+1).getVGName();
                temp = hasVG.get(i+1);
            }
        }
        for (int i = 0; i < noVG.size() - 1; i++) {
            while (noVG.get(i).getName().compareTo(noVG.get(i+1).getName()) > 0) {
                PhysicalVolume temp = noVG.get(i+1);
                noVG.set(i+1, noVG.get(i));
                noVG.set(i, temp);
                i--;
            }
        }
        for (PhysicalVolume a : hasVG) {
            if (!a.getName().equals("thisisatemporaryphysicalvolumepleasedisregardthislongstringliteral")) {
                System.out.println(a.getName() + " [" + a.getStorageNum() + "G] [" + a.getVGName() + "] [" + a.getUuid() + "]");
            }
        }
        for (PhysicalVolume b : noVG) {
            if (!b.getName().equals("thisisatemporaryphysicalvolumepleasedisregardthislongstringliteral")) {
                System.out.println(b.getName() + " [" + b.getStorageNum() + "G] [" + b.getUuid() +"]");
            }
        }
    }


    public void createVolumeGroup(String response) {
        int idx = response.indexOf(" ");
        if (checkArgs(idx, response)) {
            String[] info = separateInfo(response);
            String name = info[0];
            String pvName = info[1];
            boolean c = true;
            for (VolumeGroup vg : volumeGroups) {
                if (vg.getName().equals(name)) {
                    System.out.println("Error: VG Name In Use");
                    c = false;
                    break;
                }
            }
            PhysicalVolume temp = null;
            for (PhysicalVolume pv : physicalVolumes) {
                if (pv.getName().equals(pvName)) {
                    temp = pv;
                }
            }
            if (temp == null) {
                System.out.println("Error: PV not found");
            }
            else if (c){
                VolumeGroup newVG = new VolumeGroup(name, temp);
                if (newVG.hasPV(temp)) {
                    System.out.println("Success: Volume Group Created");
                    volumeGroups.add(newVG);
                    temp.setVG(newVG);
                }
            }
        }
    }

    public void extendVG(String response) {
        int idx = response.indexOf(" ");
        if (checkArgs(idx, response)) {
            String[] info = separateInfo(response);
            String name = info[0];
            String pvName = info[1];
            VolumeGroup a = null;
            for (VolumeGroup vg : volumeGroups) {
                if (vg.getName().equals(name)) {
                    a = vg;
                }
            }
            PhysicalVolume b = null;
            for (PhysicalVolume pv : physicalVolumes) {
                if (pv.getName().equals(pvName)) {
                    b = pv;
                }
            }
            if (a != null && b != null) {
                System.out.println("Success: " + pvName + " is extended to " + name);
                a.extend(b);
                System.out.println("New Storage Amount: " + a.getStorageNum() + "G");
            }
            else if (a != null) {
                System.out.println("Error: PV Not Found");
            }
            else {
                System.out.println("Error: VG Not Found");
            }
        }
    }

    public void listVG() {
        for (int i = 0; i < volumeGroups.size() - 1; i++) {
            while (volumeGroups.get(i).getName().compareTo(volumeGroups.get(i+1).getName()) > 0) {
                VolumeGroup temp = volumeGroups.get(i+1);
                volumeGroups.set(i+1, volumeGroups.get(i));
                volumeGroups.set(i, temp);
            }
        }
        for (VolumeGroup vg : volumeGroups) {
            System.out.println(vg.getName() + ": total:[" + vg.getStorageNum() + "G] available:[" + vg.getFreeStorage() + "G] [" + vg.allAssociates() + "] [" + vg.getUuid() + "]");
        }
    }

    public void createLV(String response) {
        int idx = response.indexOf(" ");
        String rTemp = response.substring(idx + 1);
        idx = rTemp.indexOf(" ");
        String lvName = "";
        if (idx != -1) {
            lvName = rTemp.substring(0, idx);
        }
        if (checkArgs(idx, rTemp)) {
            String[] info = separateInfo(rTemp);
            String vgName = info[0];
            String size = info[1];

            VolumeGroup vg = null;
            for (VolumeGroup vgs : volumeGroups) {
                if (vgs.getName().equals(vgName)) {
                    vg = vgs;
                }
            }
            boolean c = true;
            for (LogicalVolume lvs : logicalVolumes) {
                if (lvs.getName().equals(lvName)) {
                    System.out.println("Error: Logical Volume Name In Use");
                    c = false;
                }
            }
            if (vg != null && c) {
                LogicalVolume newLV = new LogicalVolume(lvName, size, vg);
                if (newLV.getVG() != null) {
                    vg.addLV(newLV);
                    logicalVolumes.add(newLV);
                    System.out.println("Success: Logical Volume " + lvName + " Has Been Created");
                }
            }
            else if (vg == null) {
                System.out.println("Error: VG Not Found");
            }
        }
    }

    public void extendLV(String response) {
        int idx = response.indexOf(" ");
        if (checkArgs(idx, response)) {
            String[] info = separateInfo(response);
            String lvName = info[0];
            String value = info[1];
            LogicalVolume temp = null;
            for (LogicalVolume lvs : logicalVolumes) {
                if (lvs.getName().equals(lvName)) {
                    temp = lvs;
                }
            }
            if (temp != null) {
                temp.extend(value);
            }
            else {
                System.out.println("Error: LV Not Found");
            }
        }
    }

    public void listLV() {
        ArrayList<LogicalVolume> sort = new ArrayList<>(logicalVolumes);
        for (int i = 0; i < sort.size() - 1; i++) {
            while (sort.get(i).getVG().getName().compareTo(sort.get(i+1).getVG().getName()) > 0) {
                LogicalVolume temp = sort.get(i+1);
                sort.set(i+1, sort.get(i));
                sort.set(i, temp);
                i--;
            }
        }
        for (int i = 0; i < sort.size() - 1; i++) {
            // I hate sorting
            while (sort.get(i).getName().compareTo(sort.get(i+1).getName()) > 0 && sort.get(i).getVG().getName().equals(sort.get(i).getName())) {
                LogicalVolume temp = sort.get(i+1);
                sort.set(i+1, sort.get(i));
                sort.set(i, temp);
                i--;
            }
        }
        for (LogicalVolume lv : sort) {
            System.out.println(lv.getName() + ": [" + lv.getStorageNum() + "G] [" + lv.getVG().getName() + "] [" + lv.getUuid() + "]");
        }
    }

    // Returns true if the # of Arguments for object creation is correct
    private boolean checkArgs(int idx, String r) {
        if (idx == -1) {
            System.out.println("Error: Command Has Too Few Arguments");
            return false;
        }
        else {
            String temp = r;
            for (int i = 0; i < 2; i++) {
                temp = temp.substring(idx + 1);
                idx = temp.indexOf(" ");
                if (idx == -1 && i == 0) {
                    System.out.println("Error: Command Has Too Few Arguments");
                    return false;
                }
                else if (idx != -1 && i == 1) {
                    System.out.println("Error: Command Has Too Many Arguments");
                    return false;
                }
            }
        }
        return true;
    }

    private String[] separateInfo(String response) {
        int idx = response.indexOf(" ");
        String name = response.substring(idx + 1);
        idx = name.indexOf(" ");
        String arg2 = name.substring(idx + 1);
        name = name.substring(0, idx);
        String[] out = new String[2];
        out[0] = name;
        out[1] = arg2;
        return out;
    }
}
