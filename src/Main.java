import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        String response;
        int size, idx;
        while (!exit) {
            System.out.print("$");
            response = scanner.nextLine();
            if (response.toLowerCase().contains("install-drive")) {
                idx = response.indexOf(" ");
                String name = response.substring(idx+1);
                idx = name.indexOf(" ");
                size = Integer.parseInt(name.substring(idx+1));
                name = name.substring(0, idx);

                ArrayList<String> existingNames = PhysicalHDD.getHDDNames();
                boolean isAvailable = true;
                for (String names : existingNames) {
                    if (names.equals(name)) {
                        isAvailable = false;
                        break;
                    }
                }
                if (isAvailable) {
                    PhysicalHDD newDrive = new PhysicalHDD(name, size);
                    System.out.println("Drive " + name + " has been installed.");
                }
                else {
                    System.out.println("Another Hard-drive is already assigned to this name!");
                }
            }
            else if (response.toLowerCase().contains("exit")) {
                exit = true;
            }
        }

    }
}
