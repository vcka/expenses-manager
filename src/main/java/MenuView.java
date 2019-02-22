import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Scanner;

class MenuView {
    private static final String MAIN_MENU = "Main menu";
    private static final String INCOME_MENU = "Income menu";
    private static final String EXPENSES_MENU = "Expenses menu";
    private static final String REPORTS_MENU = "Reports menu";
    private static final String EXIT = "Exit";

    private static final String MONTHLY_EXPENSES = "Monthly expenses";
    private static final String YEARLY_EXPENSES = "Yearly expenses";
    private static final String CATEGORIZED_EXPENSES_LIST = "Categorized expenses list";

    private static final String EXPENSES_SUB_MENU = "Expenses menu";
    private static final String ADD_EXPENSE_CATEGORY = "Add expense category";
    private static final String LIST_EXPENSES_CATEGORIES = "List expenses categories";
    private static final String ADD_EXPENSE = "Add expense";
    private static final String LIST_EXPENSES = "List expenses";
    private static final String DELETE_EXPENSE_CATEGORY = "Delete expense category";
    private static final String DELETE_EXPENSE = "Delete expense";

    private static final String INCOME_SUB_MENU = "Income menu";
    private static final String ADD_INCOME_CATEGORY = "Add income category";
    private static final String LIST_INCOMES_CATEGORIES = "List incomes categories";
    private static final String ADD_INCOME = "Add income";
    private static final String LIST_INCOMES = "List incomes";
    private static final String DELETE_INCOME_CATEGORY = "Delete income category";
    private static final String DELETE_INCOME = "Delete income";

    private PEMService pemService = new PEMService();
    static Scanner in = new Scanner(System.in);

    private final static int frameWidth = 80;

    static void printMenu() throws IOException, InterruptedException {
        printMenuHeader(MAIN_MENU);
        printMyMainMenu(EXPENSES_MENU, INCOME_MENU, REPORTS_MENU, EXIT);
        System.out.print("Enter your choice: ");
        MenuUtils.checkInput(in.nextLine());
    }

    void mainMenuLoop() throws IOException, InterruptedException {
        while (true) {
            printMenu();
            switch (MenuUtils.choice) {
                case 1:
                    expenseMenuLoop();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 2:
                    incomeMenuLoop();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 3:
                    reportsMenuLoop();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 4:
                    pemService.onExit();
                    break;
            }
        }
    }

    private void reportsMenuLoop() throws IOException, InterruptedException {
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

    private void expenseMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtils.clearScreen();
            MenuView.printMenuHeader(EXPENSES_SUB_MENU);
            printMyMainMenu(ADD_EXPENSE_CATEGORY, LIST_EXPENSES_CATEGORIES, ADD_EXPENSE, LIST_EXPENSES, DELETE_EXPENSE_CATEGORY, DELETE_EXPENSE);
            System.out.print("Enter your choice: ");
            MenuUtils.checkInput(in.nextLine());
            if (MenuUtils.choice == 0) break;
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
                    pemService.onCategoryDelete();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 6:
                    pemService.onExpenseDelete();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
            }
        }
    }

    private void incomeMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtils.clearScreen();
            MenuView.printMenuHeader(INCOME_SUB_MENU);
            printMyMainMenu(ADD_INCOME_CATEGORY, LIST_INCOMES_CATEGORIES, ADD_INCOME, LIST_INCOMES, DELETE_INCOME_CATEGORY, DELETE_INCOME);
            System.out.print("Enter your choice: ");
            MenuUtils.checkInput(in.nextLine());
            if (MenuUtils.choice == 0) break;
            switch (MenuUtils.choice) {
                case 1:
                    pemService.onAddIncomeCategory();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 2:
                    pemService.onIncomeCategoryList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 3:
                    pemService.onIncomeEntry();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 4:
                    pemService.onIncomeList();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 5:
                    pemService.onIncomeCategoryDelete();
                    MenuUtils.pressAnyEnterToContinue();
                    MenuUtils.clearScreen();
                    break;
                case 6:
                    pemService.onIncomeDelete();
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
