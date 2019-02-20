import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

class ReportService {
    private Repository repo = Repository.getRepository();

    Map<String, Double> calculateMonthlyTotal() {
        Map<String, Double> map = repo.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYearAndMonth(expense.getDate()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    Map<Integer, Double> calculateYearlyTotal() {
        Map<Integer, Double> map = repo.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYear(expense.getDate()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    Map<String, Double> calculateCategoriesTotal() {
        Map<String, Double> map = repo.getExpenseList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getCategoryNameByID(expense.getCategoryId()),
                                Collectors.summingDouble(Expense::getAmount)));
        return new TreeMap<>(map);
    }

    String getCategoryNameByID(Long categoryId) {
        return repo.getCategoryList().stream()
                .filter(category -> category.getCategoryId().equals(categoryId))
                .map(Category::getName)
                .collect(Collectors.joining());
    }
}