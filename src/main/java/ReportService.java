import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class ReportService {
    private final ExpenseIRepository expenseRepository = ExpenseIRepository.getRepository();
    private final ExpenseCategoryIRepository expenseCategoryRepository = ExpenseCategoryIRepository.getRepository();
    private final IncomeIRepository incomeRepository = IncomeIRepository.getRepository();
    private final IncomeCategoryIRepository incomeCategoryRepository = IncomeCategoryIRepository.getRepository();

    Map<String, Double> calculateMonthlyTotal() {
        Map<String, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYearAndMonth(expense.getDate()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    Map<Integer, Double> calculateYearlyTotal() {
        Map<Integer, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYear(expense.getDate()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    Map<String, Double> calculateCategoriesTotal() {
        Map<String, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getExpenseCategoryNameByID(expense.getCategoryId()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    String getExpenseCategoryNameByID(Long categoryId) {
        return getCategoryNameByID(categoryId, expenseCategoryRepository);
    }

    String getIncomeCategoryNameByID(Long categoryId) {
        return getCategoryNameByID(categoryId, incomeCategoryRepository);
    }
    @SuppressWarnings("unchecked")
    private String getCategoryNameByID(Long categoryId, IRepository repository) {
        return repository.getList().stream()
                .filter(category -> ((MoneyFlowCategory)category).getCategoryId().equals(categoryId))
                .map(name -> ((MoneyFlowCategory)name).getName())//IncomeCategory::getName
                .collect(Collectors.joining()).toString();
    }
}