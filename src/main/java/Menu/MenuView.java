package Menu;

import Util.Constants;
import Service.PEMService;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Scanner;

public class MenuView {
    public PEMService pemService = new PEMService();
    public static Scanner in = new Scanner(System.in);

    private final static int frameWidth = 80;

    public MenuView() throws IOException, InterruptedException {
    }

    public static void printMenu() throws IOException, InterruptedException {
        printMenuHeader(Constants.MAIN_MENU);
        printMyMainMenu(Constants.EXPENSES_MENU, Constants.INCOME_MENU, Constants.REPORTS_MENU, Constants.EXIT);
        System.out.print("Enter your choice: ");
        MenuUtil.checkInput(in.nextLine());
    }

    public void mainMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtil.clearScreen();
            printMenu();
            switch (MenuUtil.choice) {
                case 1:
                    expenseMenuLoop();
                    MenuUtil.clearScreen();
                    break;
                case 2:
                    incomeMenuLoop();
                    MenuUtil.clearScreen();
                    break;
                case 3:
                    reportsMenuLoop();
                    MenuUtil.clearScreen();
                    break;
                case 4:
                    pemService.onExit();
                    break;
            }
        }
    }

    public void reportsMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtil.clearScreen();
            MenuView.printMenuHeader(Constants.REPORTS_MENU);
            printMyMainMenu(Constants.MONTHLY_EXPENSES, Constants.YEARLY_EXPENSES, Constants.CATEGORIZED_EXPENSES_LIST,
                    Constants.CATEGORIZED_INCOME_LIST, Constants.MONTHLY_INCOME, Constants.MONTHLY_BUDGET, Constants.YEARLY_BUDGET);
            System.out.print("Enter your choice: ");
            MenuUtil.checkInput(in.nextLine());
            if (MenuUtil.choice == 0) break;
            switch (MenuUtil.choice) {
                case 1:
                    pemService.onMonthlyExpenseList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 2:
                    pemService.onYearlyExpenseList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 3:
                    pemService.onCategorizedExpenseList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 4:
                    pemService.onCategorizedIncomeList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 5:
                    pemService.onMonthlyIncomeList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 6:
                    pemService.onMonthlyBudget();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 7:
                    pemService.onYearlyBudget();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
            }
        }
    }

    public void expenseMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtil.clearScreen();
            MenuView.printMenuHeader(Constants.EXPENSES_SUB_MENU);
            printMyMainMenu(Constants.ADD_EXPENSE_CATEGORY, Constants.LIST_EXPENSES_CATEGORIES, Constants.ADD_EXPENSE, Constants.LIST_EXPENSES, Constants.DELETE_EXPENSE_CATEGORY, Constants.DELETE_EXPENSE);
            System.out.print("Enter your choice: ");
            MenuUtil.checkInput(in.nextLine());
            if (MenuUtil.choice == 0) break;
            switch (MenuUtil.choice) {
                case 1:
                    pemService.onAddExpenseCategory();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 2:
                    pemService.onExpenseCategoryList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 3:
                    pemService.onExpenseEntry();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 4:
                    pemService.onExpenseList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 5:
                    pemService.onExpenseCategoryDelete();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 6:
                    pemService.onExpenseDelete();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
            }
        }
    }

    public void incomeMenuLoop() throws IOException, InterruptedException {
        while (true) {
            MenuUtil.clearScreen();
            MenuView.printMenuHeader(Constants.INCOME_SUB_MENU);
            printMyMainMenu(Constants.ADD_INCOME_CATEGORY, Constants.LIST_INCOMES_CATEGORIES, Constants.ADD_INCOME, Constants.LIST_INCOMES, Constants.DELETE_INCOME_CATEGORY, Constants.DELETE_INCOME);
            System.out.print("Enter your choice: ");
            MenuUtil.checkInput(in.nextLine());
            if (MenuUtil.choice == 0) break;
            switch (MenuUtil.choice) {
                case 1:
                    pemService.onAddIncomeCategory();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 2:
                    pemService.onIncomeCategoryList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 3:
                    pemService.onIncomeEntry();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 4:
                    pemService.onIncomeList();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 5:
                    pemService.onIncomeCategoryDelete();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
                case 6:
                    pemService.onIncomeDelete();
                    MenuUtil.pressAnyEnterToContinue();
                    MenuUtil.clearScreen();
                    break;
            }
        }
    }

    public static void printMyMainMenu(String... menuCategories) {
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

    public static void printMenuHeader(String header) {
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
        System.out.println(StringUtils.center(StringUtils.center(header, frameWidth - 2), frameWidth, "|"));
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
    }

    public static void printMenuFooter() {
        System.out.println(StringUtils.rightPad("+", frameWidth - 1, "-") + "+");
    }

    public static void printMySubMenuContent(String... menuCategories) {
        for (String menuCategory : menuCategories) {
            System.out.println(StringUtils.center(StringUtils.rightPad(" " + menuCategory, frameWidth - 2), frameWidth, "|"));
        }
    }
}
