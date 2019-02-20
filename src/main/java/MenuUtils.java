import org.apache.commons.lang3.StringUtils;
import java.io.IOException;
import java.util.Scanner;

class MenuUtils {
    private PEMService pemService = new PEMService();
    private static Scanner in = new Scanner(System.in);
    private int choice;
    private final static int frameWidth = 80;

    private void printMenu() throws IOException, InterruptedException {
        printMenuHeader("Main menu");
        printMyMainMenu("Add category", "Categories list", "Expense entry"
                , "Expenses list", "Monthly expenses list", "Yearly expenses list"
                , "Categorized expenses list", "Delete expense (by number)"
                , "Delete category (by number)", "Exit");
        System.out.print("Enter your choice: ");
        checkInput(in.nextLine());
    }

    static void pressAnyEnterToContinue() {
        System.out.println("Press enter to continue...");
        in.nextLine();
    }

    private void onExit() {
        try {
            pemService.getRepo().categorySave();
            pemService.getRepo().expenseSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    void showMenu() throws IOException, InterruptedException {
        try {
            pemService.getRepo().categoryLoad();
            pemService.getRepo().expenseLoad();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No data loaded.");
        }

        do {
            printMenu();
            switch (choice) {
                case 1:
                    pemService.onAddCategory();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 2:
                    pemService.onCategoryList();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 3:
                    pemService.onExpenseEntry();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 4:
                    pemService.onExpenseList();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 5:
                    pemService.onMonthlyExpenseList();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 6:
                    pemService.onYearlyExpenseList();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 7:
                    pemService.onCategorizedExpenseList();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 8:
                    pemService.onExpenseDelete();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 9:
                    pemService.onCategoryDelete();
                    pressAnyEnterToContinue();
                    clearScreen();
                    break;
                case 0:
                    onExit();
                    break;
            }
        } while (true);
    }

    private void checkInput(String input) throws IOException, InterruptedException {
        if (input.matches("\\d")) {
            choice = Integer.parseInt(input);
        } else {
            System.out.println("Wrong choice, try again.");
            pressAnyEnterToContinue();
            clearScreen();
            printMenu();
        }
    }

    static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    private void printMyMainMenu(String... menuCategories) {
        final String x = StringUtils.rightPad("+", frameWidth - 1, "-") + "+";
        for (int i = 0; i < menuCategories.length; i++) {
            if (i == 9) {
                System.out.println(StringUtils.center(StringUtils.rightPad(" " + "0. " + menuCategories[i], frameWidth - 2), frameWidth, "|"));
            } else {
                System.out.println(StringUtils.center(StringUtils.rightPad(" " + (i + 1) + ". " + menuCategories[i], frameWidth - 2), frameWidth, "|"));
            }
        }
        System.out.println(x);
    }

    static void printMenuHeader(String header) {
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center(header, frameWidth - 2), frameWidth, "|"));
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
    }
    static void printMenuFooter() {
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
    }
    static void printMySubMenuContent(String... menuCategories) {
        for (String menuCategory : menuCategories) {
            System.out.println(StringUtils.center(StringUtils.rightPad(" " + menuCategory, frameWidth - 2), frameWidth, "|"));
        }
    }
}
