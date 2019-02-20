import java.io.File;
import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        if(args.length == 0) {
            Process p = Runtime.getRuntime().exec("cmd.exe /c start java -jar " + (new File(Application.class.getProtectionDomain().getCodeSource().getLocation().getPath())).getAbsolutePath() + " cmd");
        }
        MenuUtils menu = new MenuUtils();
        menu.showMenu();
    }
}
