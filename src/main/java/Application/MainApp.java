package Application;

import Menu.MenuUtil;
import Menu.MenuView;

import java.io.IOException;

public class MainApp {
    public static void main(String[] args) throws IOException, InterruptedException {
        MenuUtil.initConsole(args);
        MenuView menu = new MenuView();
        menu.mainMenuLoop();
    }
}
