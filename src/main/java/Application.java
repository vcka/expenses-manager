import java.io.IOException;

public class Application {
    public static void main(String[] args) throws IOException, InterruptedException {
        MenuUtils.initConsole(args);
        MenuView menu = new MenuView();
        menu.showMenu();
    }
}
