import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class ReportService {
    final ExpenseIRepository expenseRepository = ExpenseIRepository.getRepository();
    final ExpenseCategoryIRepository expenseCategoryRepository = ExpenseCategoryIRepository.getRepository();
    final IncomeIRepository incomeRepository = IncomeIRepository.getRepository();
    final IncomeCategoryIRepository incomeCategoryRepository = IncomeCategoryIRepository.getRepository();

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
                        (expense -> getCategoryNameByID((Long)expense.getCategoryId()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    String getCategoryNameByID(Long categoryId) {
        return expenseCategoryRepository.getList().stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .map(name -> (String)name.getName())
                .collect(Collectors.joining());
    }

    String getIncomeCategoryNameByID(Long categoryId) {
        return incomeCategoryRepository.getList().stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .map(name -> (String)name.getName())//IncomeCategory::getName
                .collect(Collectors.joining());
    }
}