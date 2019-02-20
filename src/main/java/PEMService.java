import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PEMService {
    private ExpenseRepository expenseRepository = ExpenseRepository.getExpenseRepository();
    private ExpenseCategoryRepository expenseCategoryRepository = ExpenseCategoryRepository.getExpenseCategoryRepository();
    private IncomeRepository incomeRepository = IncomeRepository.getIncomeRepository();
    private IncomeCategoryRepository incomeCategoryRepository = IncomeCategoryRepository.getIncomeCategoryRepository();
    private ReportService reportService = new ReportService();

    public PEMService() {
        try {
            expenseCategoryRepository.dataLoad();
            expenseRepository.dataLoad();
            incomeRepository.dataLoad();
            incomeCategoryRepository.dataLoad();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No data loaded.");
        }
    }

    private Scanner in = new Scanner(System.in);

    void onCategorizedExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Categorized expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateCategoriesTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuView.printMySubMenuContent(k + " - " + v);
                });
        MenuView.printMenuFooter();
        System.out.println("Categories total: " + total);
    }

    void onYearlyExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Yearly expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateYearlyTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuView.printMySubMenuContent(k + " - " + v);
                });
        MenuView.printMenuFooter();
        System.out.println("Total expenses sum: " + total);
    }

    void onMonthlyExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Monthly expenses");
        reportService.calculateMonthlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - " + v));
        MenuView.printMenuFooter();
    }

    void onExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Expenses");
        List<Expense> expenseList = expenseRepository.getExpenseList();
        for (int i = 0; i < expenseList.size(); i++) {
            Expense expense = expenseList.get(i);
            String catName = reportService.getCategoryNameByID(expense.getCategoryId());
            String dateString = DateUtil.dateToString(expense.getDate());
            MenuView.printMySubMenuContent((i + 1) + ". " + catName + " - " + expense.getAmount() + ", " + expense.getDescription() + ", " + dateString);
        }
        MenuView.printMenuFooter();
    }

    void onIncomeList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Incomes");
        List<Income> incomeList = incomeRepository.getIncomeList();
        for (int i = 0; i < incomeList.size(); i++) {
            Income income = incomeList.get(i);
            String catName = reportService.getIncomeCategoryNameByID(income.getCategoryId());
            String dateString = DateUtil.dateToString(income.getDate());
            MenuView.printMySubMenuContent((i + 1) + ". " + catName + " - " + income.getAmount() + ", " + income.getDescription() + ", " + dateString);
        }
        MenuView.printMenuFooter();
    }

    void onCategoryDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onCategoryList();
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= expenseCategoryRepository.getCategoryList().size()) {
            System.out.println("Category " + expenseCategoryRepository.getCategoryList().get(nr - 1).getName() + " will be removed.");
            expenseRepository.getExpenseList().removeIf(
                    expense -> expense.getCategoryId()
                            .equals(expenseCategoryRepository.getCategoryList()
                                    .get(nr - 1).getCategoryId()));
            expenseCategoryRepository.getCategoryList().remove(nr - 1);
        } else if (!expenseCategoryRepository.getCategoryList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onCategoryDelete();
        }
    }

    void onIncomeCategoryDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onIncomeCategoryList();
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= incomeCategoryRepository.getIncomeCategoryList().size()) {
            System.out.println("Category " + incomeCategoryRepository.getIncomeCategoryList().get(nr - 1).getName() + " will be removed.");
            incomeRepository.getIncomeList().removeIf(
                    income -> income.getCategoryId()
                            .equals(incomeCategoryRepository.getIncomeCategoryList()
                                    .get(nr - 1).getIncomeCategoryId()));
            incomeCategoryRepository.getIncomeCategoryList().remove(nr - 1);
        } else if (!incomeCategoryRepository.getIncomeCategoryList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onIncomeCategoryDelete();
        }
    }

    void onExpenseDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onExpenseList();
        System.out.print("Please enter the expense to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= expenseRepository.getExpenseList().size()) {
            System.out.println("Expense " + expenseRepository.getExpenseList().get(nr - 1).getDescription() + " will be removed.");
            expenseRepository.getExpenseList().remove(nr - 1);
        } else {
            System.out.println("No such expense.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onExpenseDelete();
        }
    }

    void onIncomeDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onIncomeList();
        System.out.print("Please enter the income to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= incomeRepository.getIncomeList().size()) {
            System.out.println("Income " + incomeRepository.getIncomeList().get(nr - 1).getDescription() + " will be removed.");
            incomeRepository.getIncomeList().remove(nr - 1);
        } else {
            System.out.println("No such income.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onIncomeDelete();
        }
    }


    void onExpenseEntry() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onCategoryList();
        System.out.print("Choose category number: ");
        String input = in.nextLine();
        int catChoice = checkInput(input);
        if (catChoice == 0) return;
        if (catChoice <= expenseCategoryRepository.getCategoryList().size()) {
            Category selectedCategory = expenseCategoryRepository.getCategoryList().get(catChoice - 1);
            System.out.println("You chose: " + selectedCategory.getName());
            System.out.print("Please enter the amount: ");
            String amountInput = in.nextLine();
            double amount = parseToDouble(amountInput);
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
            Expense expense = new Expense();
            expense.setCategoryId(selectedCategory.getCategoryId());
            expense.setAmount(amount);
            expense.setDescription(description);
            expense.setDate(date);
            //Store expense
            expenseRepository.getExpenseList().add(expense);
            System.out.println("Your expense is added.");
        } else {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onExpenseEntry();
        }
    }

    void onIncomeEntry() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onIncomeCategoryList();
        System.out.print("Choose category number: ");
        String input = in.nextLine();
        int catChoice = checkInput(input);
        if (catChoice == 0) return;
        if (catChoice <= incomeCategoryRepository.getIncomeCategoryList().size()) {
            IncomeCategory selectedCategory = incomeCategoryRepository.getIncomeCategoryList().get(catChoice - 1);
            System.out.println("You chose: " + selectedCategory.getName());
            System.out.print("Please enter the amount: ");
            String amountInput = in.nextLine();
            double amount = parseToDouble(amountInput);
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
            Income income = new Income();
            income.setCategoryId(selectedCategory.getIncomeCategoryId());
            income.setAmount(amount);
            income.setDescription(description);
            income.setDate(date);
            //Store expense
            incomeRepository.getIncomeList().add(income);
            System.out.println("Your income is added.");
        } else {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onIncomeEntry();
        }
    }

    void onCategoryList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Categories");
        List<Category> categoryList = expenseCategoryRepository.getCategoryList();
        for (int i = 0; i < categoryList.size(); i++) {
            Category c = categoryList.get(i);
            MenuView.printMySubMenuContent((i + 1) + ". " + c.getName());
        }
        MenuView.printMenuFooter();
    }

    void onIncomeCategoryList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Categories");
        List<IncomeCategory> categoryList = incomeCategoryRepository.getIncomeCategoryList();
        for (int i = 0; i < categoryList.size(); i++) {
            IncomeCategory c = categoryList.get(i);
            MenuView.printMySubMenuContent((i + 1) + ". " + c.getName());
        }
        MenuView.printMenuFooter();
    }

    void onAddCategory() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        System.out.print("Please enter category name: ");
        String catName = in.nextLine();
        if (catName.equals("")) return;
        boolean checkForExistingCategory = expenseCategoryRepository.getCategoryList().stream()
                .anyMatch(category -> category.getName().equals(catName));
        if (checkForExistingCategory) {
            System.out.println("Category all ready exists.");
        } else {
            Category cat = new Category(catName);
            expenseCategoryRepository.getCategoryList().add(cat);
        }
    }

    void onAddIncomeCategory() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        System.out.print("Please enter category name: ");
        String catName = in.nextLine();
        if (catName.equals("")) return;
        boolean checkForExistingCategory = incomeCategoryRepository.getIncomeCategoryList().stream()
                .anyMatch(category -> category.getName().equals(catName));
        if (checkForExistingCategory) {
            System.out.println("Category all ready exists.");
        } else {
            IncomeCategory cat = new IncomeCategory(catName);
            incomeCategoryRepository.getIncomeCategoryList().add(cat);
        }
    }

    int checkInput(String input) {
        if (input.isEmpty()) return 0;
        if (input.matches("\\d")) {
            return Integer.parseInt(input);
        } else {
            System.out.print("Wrong choice, try again: ");
            checkInput(in.nextLine());
        }
        return 0;
    }

    double parseToDouble(String input) {
        double value = 0D;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            if (value == 0D) return value;
            System.out.print("Wrong sum, try again: ");
            parseToDouble(in.nextLine());
        }
        return value;
    }
    void onExit() {
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
