import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        RunnerCommands cmd = new RunnerCommands();
        cmd.load();
        Path currentPath = Paths.get("");
        String p = currentPath.toAbsolutePath().toString();
        boolean exit = false;
        String response;
        while (!exit) {
            System.out.print(p + ">");
            response = scanner.nextLine();
            String r = response.toLowerCase();
            for (int i = response.length() - 1; i > 0; i--) {
                if (response.charAt(i) == ' ') {
                    response = response.substring(0, i);
                }
                else {
                    break;
                }
            }
            if (r.contains("install-drive")) {
                cmd.installDrive(response);
            }
            else if (r.contains("list-drives")) {
                cmd.listDrives();
            }
            else if (r.contains("pvcreate")) {
                cmd.createPhysicalVolume(response);
            }
            else if (r.contains("pvlist")) {
                cmd.listPV();
            }
            else if (r.contains("vgcreate")) {
                cmd.createVolumeGroup(response);
            }
            else if (r.contains("vgextend")) {
                cmd.extendVG(response);
            }
            else if (r.contains("vglist")) {
                cmd.listVG();
            }
            else if (r.contains("lvcreate")) {
                cmd.createLV(response);
            }
            else if (r.contains("lvextend")) {
                cmd.extendLV(response);
            }
            else if (r.contains("lvlist")) {
                cmd.listLV();
            }
            else if (r.contains("exit")) {
                cmd.save();
                exit = true;
            }
            else {
                System.out.println("Error: Command not recognized");
            }
            cmd.save();
            System.out.println();
        }
    }
}
