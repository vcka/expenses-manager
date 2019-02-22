import java.io.File;
import java.io.IOException;

public class MenuUtil {
    static int choice;

    static void pressAnyEnterToContinue() {
        System.out.println("Press enter to continue...");
        MenuView.in.nextLine();
    }

    static void initConsole(String[] args) throws IOException {
        if (args.length == 0) {
            Process p = Runtime.getRuntime().exec("cmd.exe /c start java -jar " +
                    (new File(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getAbsolutePath() + " cmd");
        }
    }

    static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    static int checkInput(String input) throws IOException, InterruptedException {
        if (input.matches("\\d")) {
            choice = Integer.parseInt(input);
        } else if (input.matches("")) {
            clearScreen();
            choice = 0;
        } else {
            System.out.println("Wrong choice, try again.");
            pressAnyEnterToContinue();
            clearScreen();
            return choice;
        }
        return choice;
    }
}
