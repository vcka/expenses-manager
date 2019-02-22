import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PEMService {
    final ExpenseIRepository expenseRepository = ExpenseIRepository.getRepository();
    final ExpenseCategoryIRepository expenseCategoryRepository = ExpenseCategoryIRepository.getRepository();
    final IncomeIRepository incomeRepository = IncomeIRepository.getRepository();
    final IncomeCategoryIRepository incomeCategoryRepository = IncomeCategoryIRepository.getRepository();
    private ReportService reportService = new ReportService();

    PEMService() {
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
        System.out.println();
        List<MoneyFlow> moneyFlowList = expenseRepository.getList();
        for (int i = 0; i < moneyFlowList.size(); i++) {
            MoneyFlow moneyFlow = moneyFlowList.get(i);
            String catName = reportService.getCategoryNameByID((Long) moneyFlow.getCategoryId());
            String dateString = DateUtil.dateToString((Date) moneyFlow.getDate());
            MenuView.printMySubMenuContent((i + 1) + ". " + catName + " - " + moneyFlow.getAmount() + ", " + moneyFlow.getDescription() + ", " + dateString);
        }
        MenuView.printMenuFooter();
    }

    void onIncomeList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Incomes");
        List<MoneyFlow> incomeList = incomeRepository.getList();
        for (int i = 0; i < incomeList.size(); i++) {
            MoneyFlow income = incomeList.get(i);
            String catName = reportService.getIncomeCategoryNameByID((Long) income.getCategoryId());
            String dateString = DateUtil.dateToString((Date) income.getDate());
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
        if (nr <= expenseCategoryRepository.getList().size()) {
            System.out.println("Category " + expenseCategoryRepository.getList().get(nr - 1).getName() + " will be removed.");
            expenseRepository.getList().removeIf(
                    moneyFlow -> moneyFlow.getCategoryId()
                            .equals(expenseCategoryRepository.getList()
                                    .get(nr - 1).getCategoryId()));
            expenseCategoryRepository.getList().remove(nr - 1);
        } else if (!expenseCategoryRepository.getList().isEmpty()) {
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
        if (nr <= incomeCategoryRepository.getList().size()) {
            System.out.println("Category " + incomeCategoryRepository.getList().get(nr - 1).getName() + " will be removed.");
            incomeRepository.getList().removeIf(
                    income -> income.getCategoryId()
                            .equals(incomeCategoryRepository.getList()
                                    .get(nr - 1).getCategoryId()));
            incomeCategoryRepository.getList().remove(nr - 1);
        } else if (!incomeCategoryRepository.getList().isEmpty()) {
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
        if (nr <= expenseRepository.getList().size()) {
            System.out.println("MoneyFlow " + expenseRepository.getList().get(nr - 1) + " will be removed.");
            expenseRepository.getList().remove(nr - 1);
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
        if (nr <= incomeRepository.getList().size()) {
            System.out.println("Income " + incomeRepository.getList().get(nr - 1).getDescription() + " will be removed.");
            incomeRepository.getList().remove(nr - 1);
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
        if (catChoice <= expenseCategoryRepository.getList().size()) {
            Category selectedCategory = expenseCategoryRepository.getList().get(catChoice - 1);
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
            MoneyFlow moneyFlow = new MoneyFlow();
            moneyFlow.setCategoryId(selectedCategory.getCategoryId());
            moneyFlow.setAmount(amount);
            moneyFlow.setDescription(description);
            moneyFlow.setDate(date);
            //Store moneyFlow
            expenseRepository.getList().add(moneyFlow);
            System.out.println("Your moneyFlow is added.");
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
        if (catChoice <= incomeCategoryRepository.getList().size()) {
            IncomeCategory selectedCategory = incomeCategoryRepository.getList().get(catChoice - 1);
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
            MoneyFlow income = new MoneyFlow();
            income.setCategoryId(selectedCategory.getCategoryId());
            income.setAmount(amount);
            income.setDescription(description);
            income.setDate(date);
            //Store expense
            incomeRepository.getList().add(income);
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
        List<Category> categoryList = expenseCategoryRepository.getList();
        for (int i = 0; i < categoryList.size(); i++) {
            Category c = categoryList.get(i);
            MenuView.printMySubMenuContent((i + 1) + ". " + c.getName());
        }
        MenuView.printMenuFooter();
    }

    void onIncomeCategoryList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuView.printMenuHeader("Categories");
        List<IncomeCategory> categoryList = incomeCategoryRepository.getList();
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
        boolean checkForExistingCategory = expenseCategoryRepository.getList().stream()
                .anyMatch(category -> category.getName().equals(catName));
        if (checkForExistingCategory) {
            System.out.println("Category all ready exists.");
        } else {
            Category cat = new Category(catName);
            expenseCategoryRepository.getList().add(cat);
        }
    }

    void onAddIncomeCategory() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        System.out.print("Please enter category name: ");
        String catName = in.nextLine();
        if (catName.equals("")) return;
        boolean checkForExistingCategory = incomeCategoryRepository.getList().stream()
                .anyMatch(category -> category.getName().equals(catName));
        if (checkForExistingCategory) {
            System.out.println("Category all ready exists.");
        } else {
            IncomeCategory cat = new IncomeCategory(catName);
            incomeCategoryRepository.getList().add(cat);
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
