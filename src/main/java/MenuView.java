import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Scanner;

class MenuView {
    public static final String REPORTS_MENU = "Reports menu";
    public static final String MAIN_MENU = "Main menu";
    public static final String ADD_EXPENSES_CATEGORY = "Add expenses category";
    public static final String CATEGORIES_LIST = "Categories list";
    public static final String EXPENSE_ENTRY = "Expense entry";
    public static final String EXPENSES_LIST = "Expenses list";
    public static final String DELETE_EXPENSE_BY_NUMBER = "Delete expense (by number)";
    public static final String DELETE_CATEGORY_BY_NUMBER = "Delete category (by number)";
    public static final String EXIT = "Exit";
    public static final String MONTHLY_EXPENSES = "Monthly expenses";
    public static final String YEARLY_EXPENSES = "Yearly expenses";
    public static final String CATEGORIZED_EXPENSES_LIST = "Categorized expenses list";
    private PEMService pemService = new PEMService();
    static Scanner in = new Scanner(System.in);

    private final static int frameWidth = 80;

    static void printMenu() throws IOException, InterruptedException {
        printMenuHeader(MAIN_MENU);
        printMyMainMenu(ADD_EXPENSES_CATEGORY, CATEGORIES_LIST, EXPENSE_ENTRY,
                EXPENSES_LIST, REPORTS_MENU, DELETE_EXPENSE_BY_NUMBER,
                DELETE_CATEGORY_BY_NUMBER, EXIT);
        System.out.print("Enter your choice: ");
        MenuUtils.checkInput(in.nextLine());
    }

    void mainMenuLoop() throws IOException, InterruptedException {
        while (true) {
            printMenu();
            switch (MenuUtils.choice) {
                case 1:
                    pemService.onAddCategory();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 2:
                    pemService.onCategoryList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 3:
                    pemService.onExpenseEntry();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 4:
                    pemService.onExpenseList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 5:
//                    pemService.onMonthlyExpenseList();
                    reportsMenuLoop();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
//                case 6:
//                    pemService.onYearlyExpenseList();
//
//                    pressAnyEnterToContinue();
//                    clearScreen();
//                    break;
//                case 7:
//                    pemService.onCategorizedExpenseList();
//                    pressAnyEnterToContinue();
//                    clearScreen();
//                    break;
                case 6:
                    pemService.onExpenseDelete();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 7:
                    pemService.onCategoryDelete();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 0:
                    pemService.onExit();
                    break;
            }
        }
    }

    void reportsMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtils.clearScreen();
            MenuView.printMenuHeader(REPORTS_MENU);
            printMyMainMenu(MONTHLY_EXPENSES, YEARLY_EXPENSES, CATEGORIZED_EXPENSES_LIST);
            System.out.print("Enter your choice: ");
            MenuUtils.checkInput(in.nextLine());
            if (MenuUtils.choice == 0) break;
            switch (MenuUtils.choice) {
                case 1:
                    pemService.onMonthlyExpenseList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 2:
                    pemService.onYearlyExpenseList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 3:
                    pemService.onCategorizedExpenseList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
            }
        }
    }

    private static void printMyMainMenu(String... menuCategories) {
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
