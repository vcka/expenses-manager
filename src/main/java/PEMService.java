import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

class PEMService {
    private Repository repo = Repository.getRepository();
    private ReportService reportService = new ReportService();

    Repository getRepo() {
        return repo;
    }

    private Scanner in = new Scanner(System.in);

    void onCategorizedExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuUtils.printMenuHeader("Categorized expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateCategoriesTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuUtils.printMySubMenuContent(k + " - " + v);
                });
        MenuUtils.printMenuFooter();
        System.out.println("Categories total: " + total);
    }

    void onYearlyExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuUtils.printMenuHeader("Yearly expenses");
        AtomicReference<Double> total = new AtomicReference<>(0.0D);
        reportService.calculateYearlyTotal()
                .forEach((k, v) -> {
                    total.updateAndGet(v1 -> v1 + v);
                    MenuUtils.printMySubMenuContent(k + " - " + v);
                });
        MenuUtils.printMenuFooter();
        System.out.println("Total expenses sum: " + total);
    }

    void onMonthlyExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuUtils.printMenuHeader("Monthly expenses");
        reportService.calculateMonthlyTotal()
                .forEach((k, v) -> MenuUtils.printMySubMenuContent(k + " - " + v));
        MenuUtils.printMenuFooter();
    }

    void onExpenseList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuUtils.printMenuHeader("Expenses");
        List<Expense> expenseList = repo.getExpenseList();
        for (int i = 0; i < expenseList.size(); i++) {
            Expense expense = expenseList.get(i);
            String catName = reportService.getCategoryNameByID(expense.getCategoryId());
            String dateString = DateUtil.dateToString(expense.getDate());
            MenuUtils.printMySubMenuContent((i + 1) + ". " + catName + " - " + expense.getAmount() + ", " + expense.getDescription() + ", " + dateString);
        }
        MenuUtils.printMenuFooter();
    }

    void onCategoryDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onCategoryList();
        System.out.print("Please enter the category to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= repo.getCategoryList().size()) {
            System.out.println("Category " + repo.getCategoryList().get(nr - 1).getName() + " will be removed.");
            repo.getExpenseList().removeIf(expense -> expense.getCategoryId().equals(repo.getCategoryList().get(nr - 1).getCategoryId()));
            repo.getCategoryList().remove(nr - 1);
        } else if (!repo.getCategoryList().isEmpty()) {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onCategoryDelete();
        }
    }

    void onExpenseDelete() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onExpenseList();
        System.out.print("Please enter the expense to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= repo.getExpenseList().size()) {
            System.out.println("Expense " + repo.getExpenseList().get(nr - 1).getDescription() + " will be removed.");
            repo.getExpenseList().remove(nr - 1);
        } else {
            System.out.println("No such expense.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onExpenseDelete();
        }
    }


    void onExpenseEntry() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        onCategoryList();
        System.out.print("Choose category number: ");
        String input = in.nextLine();
        int catChoice = checkInput(input);
        if (catChoice == 0) return;
        if (catChoice <= repo.getCategoryList().size()) {
            Category selectedCategory = repo.getCategoryList().get(catChoice - 1);
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
            repo.getExpenseList().add(expense);
            System.out.println("Your expense is added.");
        } else {
            System.out.println("No such category.");
            MenuUtils.pressAnyEnterToContinue();
            MenuUtils.clearScreen();
            onExpenseEntry();
        }
    }

    void onCategoryList() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        MenuUtils.printMenuHeader("Categories");
        List<Category> categoryList = repo.getCategoryList();
        for (int i = 0; i < categoryList.size(); i++) {
            Category c = categoryList.get(i);
            MenuUtils.printMySubMenuContent((i + 1) + ". " + c.getName());
        }
        MenuUtils.printMenuFooter();
    }

    void onAddCategory() throws IOException, InterruptedException {
        MenuUtils.clearScreen();
        System.out.print("Please enter category name: ");
        String catName = in.nextLine();
        if (catName.equals("")) return;
        boolean checkForExistingCategory = repo.getCategoryList().stream()
                .anyMatch(category -> category.getName().equals(catName));
        if (checkForExistingCategory) {
            System.out.println("Category all ready exists.");
        } else {
            Category cat = new Category(catName);
            repo.getCategoryList().add(cat);

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
}
