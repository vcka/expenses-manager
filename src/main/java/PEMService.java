import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

class PEMService {
    private final ExpenseIRepository expenseRepository = ExpenseIRepository.getRepository();
    private final ExpenseCategoryIRepository expenseCategoryRepository = ExpenseCategoryIRepository.getRepository();
    private final IncomeIRepository incomeRepository = IncomeIRepository.getRepository();
    private final IncomeCategoryIRepository incomeCategoryRepository = IncomeCategoryIRepository.getRepository();
    private final ReportService reportService = new ReportService();
    private final PEMServiceHelper serviceHelper = new PEMServiceHelper();

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

    public void onCategorizedExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
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

    public void onYearlyExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
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

    public void onMonthlyExpenseList() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Monthly expenses");
        reportService.calculateMonthlyTotal()
                .forEach((k, v) -> MenuView.printMySubMenuContent(k + " - " + v));
        MenuView.printMenuFooter();
    }

    public void onCategoryDelete(IRepository repository) throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        if (repository.getClass().getName().equals("IncomeIRepository$1")) {
            onIncomeCategoryList();
        } else {
            onExpenseCategoryList();
        }
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = serviceHelper.checkInput(input);
        if (nr == 0) return;
        if (nr <= repository.getList().size()) {
//            System.out.println("Category " + expenseCategoryRepository.getList().get(nr - 1).getName() + " will be removed.");
            expenseRepository.getList().removeIf(
                    moneyFlow -> moneyFlow.getCategoryId()
                            .equals(expenseCategoryRepository.getList()
                                    .get(nr - 1).getCategoryId()));
            expenseCategoryRepository.getList().remove(nr - 1);
        } else if (!expenseCategoryRepository.getList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onExpenseCategoryDelete();
        }
    }


    public void onExpenseCategoryDelete() throws IOException, InterruptedException {

        MenuUtil.clearScreen();
        onExpenseCategoryList();
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = serviceHelper.checkInput(input);
        if (nr == 0) return;
        if (nr <= expenseCategoryRepository.getList().size()) {
            System.out.println("MoneyFlowCategory " + expenseCategoryRepository.getList().get(nr - 1).getName() + " will be removed.");
            expenseRepository.getList().removeIf(
                    moneyFlow -> moneyFlow.getCategoryId()
                            .equals(expenseCategoryRepository.getList()
                                    .get(nr - 1).getCategoryId()));
            expenseCategoryRepository.getList().remove(nr - 1);
        } else if (!expenseCategoryRepository.getList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onExpenseCategoryDelete();
        }
    }

    public void onIncomeCategoryDelete() throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        onIncomeCategoryList();
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = serviceHelper.checkInput(input);
        if (nr == 0) return;
        if (nr <= incomeCategoryRepository.getList().size()) {
            System.out.println("MoneyFlowCategory " + incomeCategoryRepository.getList().get(nr - 1).getName() + " will be removed.");
            incomeRepository.getList().removeIf(
                    income -> income.getCategoryId()
                            .equals(incomeCategoryRepository.getList()
                                    .get(nr - 1).getCategoryId()));
            incomeCategoryRepository.getList().remove(nr - 1);
        } else if (!incomeCategoryRepository.getList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onIncomeCategoryDelete();
        }
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
            moneyFlow.setAmount(amount);
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

    public void onExpenseCategoryList() throws IOException, InterruptedException {
        serviceHelper.onCategoryList(expenseCategoryRepository);
    }

    public void onIncomeCategoryList() throws IOException, InterruptedException {
        serviceHelper.onCategoryList(incomeCategoryRepository);
    }

    public void onAddIncomeCategory() throws IOException, InterruptedException {
        serviceHelper.onAddECategory(incomeCategoryRepository);
    }

    public void onAddExpenseCategory() throws IOException, InterruptedException {
        serviceHelper.onAddECategory(expenseCategoryRepository);
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
