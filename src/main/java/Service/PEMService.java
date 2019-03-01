package Service;

import Expense.ExpenseCategoryRepository;
import Expense.ExpenseRepository;
import Income.IncomeCategoryRepository;
import Util.DateUtil;
import Income.IncomeRepository;
import Menu.MenuUtil;
import Menu.MenuView;
import MoneyFlow.MoneyFlow;
import MoneyFlow.MoneyFlowCategory;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class PEMService {
    public static ExpenseRepository expenseRepository = ExpenseRepository.getRepository();
    public static ExpenseCategoryRepository expenseCategoryRepository = ExpenseCategoryRepository.getRepository();
    public static IncomeRepository incomeRepository = IncomeRepository.getRepository();
    public static IncomeCategoryRepository incomeCategoryRepository = IncomeCategoryRepository.getRepository();
    public ReportService reportService = new ReportService();
    public static PEMServiceHelper serviceHelper = new PEMServiceHelper();

    public PEMService() throws IOException, InterruptedException {
        try {
            expenseCategoryRepository.dataLoad();
            expenseRepository.dataLoad();
            incomeRepository.dataLoad();
            incomeCategoryRepository.dataLoad();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No data loaded.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
        }
    }

    private Scanner in = new Scanner(System.in);

    public void onCategorizedExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Categorized expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateExpenseCategoriesTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuView.printMySubMenuContent(k + " - " + Math.abs(v));
                });
        MenuView.printMenuFooter();
        System.out.println("Categories total: " + Math.abs(total.get()));
    }

    public void onCategorizedIncomeList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Categorized income");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateIncomeCategoriesTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuView.printMySubMenuContent(k + " - " + Math.abs(v));
                });
        MenuView.printMenuFooter();
        System.out.println("Categories total: " + Math.abs(total.get()));
    }

    public void onYearlyExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Yearly expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateExpenseYearlyTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuView.printMySubMenuContent(k + " - (" + Math.abs(v) + ")");
                });
        MenuView.printMenuFooter();
        System.out.println("Total expenses sum: " + Math.abs(total.get()));
    }

    public void onMonthlyExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Monthly expenses");
        reportService.calculateExpenseMonthlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - (" + Math.abs(v) + ")"));
        MenuView.printMenuFooter();
    }

    public void onMonthlyIncomeList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Monthly income");
        reportService.calculateIncomeMonthlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - (" + v + ")"));
        MenuView.printMenuFooter();
    }

    public void onMonthlyBudget() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Monthly budget");
        reportService.calculateBudgetMonthlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - (" + v + ")"));
        MenuView.printMenuFooter();
    }

    public void onYearlyBudget() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Yearly budget");
        reportService.calculateBudgetYearlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - (" + v +")"));
        MenuView.printMenuFooter();
    }

    public void onExpenseEntry() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        onExpenseCategoryList();
        System.out.print("Choose category number: ");
        String input = in.nextLine();
        int catChoice = serviceHelper.checkInput(input);
        if (catChoice == 0) return;
        if (catChoice <= expenseCategoryRepository.getList().size()) {
            MoneyFlowCategory selectedMoneyFlowCategory = expenseCategoryRepository.getList().get(catChoice - 1);
            System.out.println("You chose: " + selectedMoneyFlowCategory.getName());
            System.out.print("Please enter the amount: ");
            String amountInput = in.nextLine();
            double amount = serviceHelper.parseToDouble(amountInput);
            if (amount == 0D) return;
            System.out.print("Please write description: ");
            String description = in.nextLine();
            if (description.equals("")) return;
            System.out.print("Enter date (yyyy MM dd): ");
            Date date;
            do {
                date = DateUtil.stringToDate(in.nextLine());
            }
            while (date == null);
            MoneyFlow moneyFlow = new MoneyFlow();
            moneyFlow.setCategoryId(selectedMoneyFlowCategory.getCategoryId());
            moneyFlow.setAmount(-amount);
            moneyFlow.setDescription(description);
            moneyFlow.setDate(date);
            //Store moneyFlow
            expenseRepository.getList().add(moneyFlow);
            System.out.println("Your expense is added.");
        } else {
            System.out.println("No such category.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onExpenseEntry();
        }
    }

    public void onIncomeEntry() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        onIncomeCategoryList();
        System.out.print("Choose category number: ");
        String input = in.nextLine();
        int catChoice = serviceHelper.checkInput(input);
        if (catChoice == 0) return;
        if (catChoice <= incomeCategoryRepository.getList().size()) {
            MoneyFlowCategory selectedMoneyFlowCategory = incomeCategoryRepository.getList().get(catChoice - 1);
            System.out.println("You chose: " + selectedMoneyFlowCategory.getName());
            System.out.print("Please enter the amount: ");
            String amountInput = in.nextLine();
            double amount = serviceHelper.parseToDouble(amountInput);
            if (amount == 0D) return;
            System.out.print("Please write description: ");
            String description = in.nextLine();
            if (description.equals("")) return;
            System.out.print("Enter date (yyyy MM dd): ");
            Date date;
            do {
                date = DateUtil.stringToDate(in.nextLine());
            }
            while (date == null);
            MoneyFlow moneyFlow = new MoneyFlow();
            moneyFlow.setCategoryId(selectedMoneyFlowCategory.getCategoryId());
            moneyFlow.setAmount(amount);
            moneyFlow.setDescription(description);
            moneyFlow.setDate(date);
            //Store expense
            incomeRepository.getList().add(moneyFlow);
            System.out.println("Your income is added.");
        } else {
            System.out.println("No such category.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onIncomeEntry();
        }
    }

    public void onExpenseList() throws IOException, InterruptedException {
        serviceHelper.onList(expenseRepository);
    }

    public void onIncomeList() throws IOException, InterruptedException {
        serviceHelper.onList(incomeRepository);
    }

    public void onExpenseDelete() throws IOException, InterruptedException {
        serviceHelper.onFlowDelete(expenseRepository);
    }

    public void onIncomeDelete() throws IOException, InterruptedException {
        serviceHelper.onFlowDelete(incomeRepository);
    }

    public static void onExpenseCategoryList() throws IOException, InterruptedException {
        serviceHelper.onCategoryList(expenseCategoryRepository);
    }

    public static void onIncomeCategoryList() throws IOException, InterruptedException {
        serviceHelper.onCategoryList(incomeCategoryRepository);
    }

    public void onAddIncomeCategory() throws IOException, InterruptedException {
        serviceHelper.onAddECategory(incomeCategoryRepository);
    }

    public void onAddExpenseCategory() throws IOException, InterruptedException {
        serviceHelper.onAddECategory(expenseCategoryRepository);
    }

    public void onExpenseCategoryDelete() throws IOException, InterruptedException {
        serviceHelper.onCategoryDelete(expenseCategoryRepository);
    }

    public void onIncomeCategoryDelete() throws IOException, InterruptedException {
        serviceHelper.onCategoryDelete(incomeCategoryRepository);
    }

    public void onExit() {
        try {
            expenseCategoryRepository.dataSave();
            expenseRepository.dataSave();
            incomeCategoryRepository.dataSave();
            incomeRepository.dataSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
