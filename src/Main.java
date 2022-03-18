import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RunnerCommands cmd = new RunnerCommands();
        boolean exit = false;
        String response;
        while (!exit) {
            System.out.print("$");
            response = scanner.nextLine();
            String r = response.toLowerCase();
            if (r.contains("install-drive")) {
                cmd.installDrive(response);
            }
            else if (r.contains("list-drives")) {
                cmd.listDrives();
            }
            else if (r.contains("pvcreate")) {
                cmd.createPhysicalVolume(response);
            }
            else if (r.contains("exit")) {
                exit = true;
            }
            else {
                System.out.println("Error: Command not recognized");
            }
        }

    }
}
