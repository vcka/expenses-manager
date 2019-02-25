import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class PEMServiceHelper {
    private final ReportService reportService = new ReportService();
    private Scanner in = new Scanner(System.in);

    @SuppressWarnings("unchecked")
    public void onAddECategory(IRepository repository) throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        System.out.print("Please enter category name: ");
        String catName = in.nextLine();
        if (catName.equals("")) return;
        if (checkForExistingCategory(catName, repository)) {
            System.out.println("MoneyFlowCategory all ready exists.");
        } else {
            MoneyFlowCategory cat = new MoneyFlowCategory(catName);
            repository.getList().add(cat);
        }
    }

    @SuppressWarnings("unchecked")
    public void onCategoryList(IRepository repository) throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        MenuView.printMenuHeader("Categories");
        List<MoneyFlowCategory> moneyFlowCategoryList = repository.getList();
        for (int i = 0; i < moneyFlowCategoryList.size(); i++) {
            MoneyFlowCategory c = moneyFlowCategoryList.get(i);
            MenuView.printMySubMenuContent((i + 1) + ". " + c.getName());
        }
        MenuView.printMenuFooter();
    }

    public void onFlowDelete(IRepository repository) throws IOException, InterruptedException {
        MenuUtil.clearScreen();
        onList(repository);
        System.out.print("Please enter number to remove: ");
        String input = in.nextLine();
        int nr = checkInput(input);
        if (nr == 0) return;
        if (nr <= repository.getList().size()) {
            System.out.println(((MoneyFlow) repository.getList().get(nr - 1)).getDescription() + " will be removed.");
            repository.getList().remove(nr - 1);
        } else {
            System.out.println("No such number.");
            MenuUtil.pressAnyEnterToContinue();
            MenuUtil.clearScreen();
            onFlowDelete(repository);
        }
    }

    @SuppressWarnings("unchecked")
    void onList(IRepository repository) throws IOException, InterruptedException {
        String catName;
        MenuUtil.clearScreen();
        MenuView.printMenuHeader(repository.getClass().getName().equals("IncomeIRepository$1") ? "Income list" : "Expenses list");
        List<MoneyFlow> incomeList = repository.getList();
        for (int i = 0; i < incomeList.size(); i++) {
            MoneyFlow income = incomeList.get(i);
            if (repository.getClass().getName().equals("IncomeIRepository$1")) {
                catName = reportService.getIncomeCategoryNameByID(income.getCategoryId());
            } else {
                catName = reportService.getExpenseCategoryNameByID(income.getCategoryId());
            }
            String dateString = DateUtil.dateToString(income.getDate());
            MenuView.printMySubMenuContent((i + 1) + ". " + catName + " - " + income.getAmount() + ", " + income.getDescription() + ", " + dateString);
        }
        MenuView.printMenuFooter();
    }

    @SuppressWarnings("unchecked")
    private boolean checkForExistingCategory(String catName, IRepository repository) {
        return repository.getList().stream()
                .anyMatch(moneyFlowCategory -> ((MoneyFlowCategory) moneyFlowCategory).getName().equals(catName));
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
