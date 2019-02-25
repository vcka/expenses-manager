import java.util.Date;
import java.util.List;
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
                        (expense -> DateUtil.getYearAndMonth((Date)expense.getDate()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    Map<Integer, Double> calculateYearlyTotal() {
        Map<Integer, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYear((Date) expense.getDate()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    Map<String, Double> calculateCategoriesTotal() {
        Map<String, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getExpenseCategoryNameByID((Long)expense.getCategoryId()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    String getExpenseCategoryNameByID(Long categoryId) {
        return getCategoryNameByID(categoryId, expenseCategoryRepository);
//        return expenseCategoryRepository.getList().stream()
//                .filter(category -> category.getCategoryId().equals(categoryId))
//                .map(name -> (String)name.getName())
//                .collect(Collectors.joining());
    }

    String getIncomeCategoryNameByID(Long categoryId) {
        return getCategoryNameByID(categoryId, incomeCategoryRepository);
//        return incomeCategoryRepository.getList().stream()
//                .filter(category -> category.getCategoryId().equals(categoryId))
//                .map(name -> (String)name.getName())//IncomeCategory::getName
//                .collect(Collectors.joining());
    }
    @SuppressWarnings("unchecked")
    private String getCategoryNameByID(Long categoryId, IRepository repository) {
        return repository.getList().stream()
                .filter(category -> ((MoneyFlowCategory)category).getCategoryId().equals(categoryId))
                .map(name -> ((MoneyFlowCategory)name).getName())//IncomeCategory::getName
                .collect(Collectors.joining()).toString();
    }
}