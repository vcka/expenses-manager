import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class ReportService {
//    private Repository repo = Repository.getRepository();
    private ExpenseRepository expenseRepository = ExpenseRepository.getExpenseRepository();
    private ExpenseCategoryRepository expenseCategoryRepository = ExpenseCategoryRepository.getExpenseCategoryRepository();
    private IncomeRepository incomeCategory = IncomeRepository.getIncomeRepository();
    private IncomeCategoryRepository incomeCategoryRepository = IncomeCategoryRepository.getIncomeCategoryRepository();


    Map<String, Double> calculateMonthlyTotal() {
        Map<String, Double> map = expenseRepository.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYearAndMonth(expense.getDate()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    Map<Integer, Double> calculateYearlyTotal() {
        Map<Integer, Double> map = expenseRepository.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYear(expense.getDate()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    Map<String, Double> calculateCategoriesTotal() {
        Map<String, Double> map = expenseRepository.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getCategoryNameByID(expense.getCategoryId()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    String getCategoryNameByID(Long categoryId) {
        return expenseCategoryRepository.getCategoryList().stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .map(Category::getName)
                .collect(Collectors.joining());
    }

    String getIncomeCategoryNameByID(Long categoryId) {
        return incomeCategoryRepository.getIncomeCategoryList().stream()
                .filter(category -> category.getIncomeCategoryId().equals(categoryId))
                .map(IncomeCategory::getName)
                .collect(Collectors.joining());
    }
}