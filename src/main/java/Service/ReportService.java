package Service;

import MoneyFlow.MoneyFlow;
import Util.DateUtil;
import Expense.ExpenseCategoryIRepository;
import Expense.ExpenseIRepository;
import Income.IncomeCategoryIRepository;
import Income.IncomeIRepository;
import MoneyFlow.IRepository;
import MoneyFlow.MoneyFlowCategory;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReportService {
    private final ExpenseIRepository expenseRepository = ExpenseIRepository.getRepository();
    private final ExpenseCategoryIRepository expenseCategoryRepository = ExpenseCategoryIRepository.getRepository();
    private final IncomeIRepository incomeRepository = IncomeIRepository.getRepository();
    private final IncomeCategoryIRepository incomeCategoryRepository = IncomeCategoryIRepository.getRepository();

    public Map<String, Double> calculateExpenseMonthlyTotal() {
//        Map<String, Double> map = expenseRepository.getList().stream()
//                .collect(Collectors.groupingBy
//                        (expense -> DateUtil.getYearAndMonth(expense.getDate()),
//                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(calculateMonthlyTotal(expenseRepository));
    }

    public Map<String, Double> calculateIncomeMonthlyTotal() {
//        Map<String, Double> map = incomeRepository.getList().stream()
//                .collect(Collectors.groupingBy
//                        (expense -> DateUtil.getYearAndMonth(expense.getDate()),
//                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(calculateMonthlyTotal(incomeRepository));
    }

    @SuppressWarnings("unchecked")
    public Map<String, Double> calculateMonthlyTotal(IRepository repository) {
        Map<String, Double> map = (Map<String, Double>) repository.getList().stream()
                .collect(Collectors.groupingBy(exp -> DateUtil.getYearAndMonth(((MoneyFlow)exp).getDate()),
                        Collectors.summingDouble(expense -> ((MoneyFlow)expense).getAmount())));

//    repository.getList().stream()
//                .collect(Collectors.groupingBy
//                        (expense -> DateUtil.getYearAndMonth( ((MoneyFlow)expense).getDate() ),
//                                Collectors.summingDouble(expense -> ((MoneyFlow)expense).getAmount())));
        return new TreeMap<>(map);
    }

    public Map<String, Double> calculateBudgetMonthlyTotal(){
        Map<String, Double> budget = Stream.concat(calculateExpenseMonthlyTotal().entrySet().stream(), calculateIncomeMonthlyTotal().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // The key
                        Map.Entry::getValue, // The value
                        // The "merger"
                        (expense, income) -> income + expense
                        )
                );
        return  new TreeMap<>(budget);
    }

    public Map<Integer, Double> calculateBudgetYearlyTotal(){
        Map<Integer, Double> budget = Stream.concat(calculateIncomeYearlyTotal().entrySet().stream(), calculateExpenseYearlyTotal().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey, // The key
                        Map.Entry::getValue, // The value
                        // The "merger"
                        (income, expense) -> income + expense
                        )
                );
        return  new TreeMap<>(budget);
    }

    public Map<Integer, Double> calculateExpenseYearlyTotal() {
        Map<Integer, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> DateUtil.getYear(expense.getDate()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    public Map<Integer, Double> calculateIncomeYearlyTotal() {
        Map<Integer, Double> map = incomeRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (income -> DateUtil.getYear(income.getDate()),
                                Collectors.summingDouble(income -> (Double)income.getAmount())));
        return new TreeMap<>(map);
    }

    public Map<String, Double> calculateIncomeCategoriesTotal() {
        Map<String, Double> map = incomeRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getIncomeCategoryNameByID(expense.getCategoryId()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    public Map<String, Double> calculateExpenseCategoriesTotal() {
        Map<String, Double> map = expenseRepository.getList().stream()
                .collect(Collectors.groupingBy
                        (expense -> getExpenseCategoryNameByID(expense.getCategoryId()),
                                Collectors.summingDouble(expense -> (Double)expense.getAmount())));
        return new TreeMap<>(map);
    }

    public String getExpenseCategoryNameByID(Long categoryId) {
        return getCategoryNameByID(categoryId, expenseCategoryRepository);
    }

    public String getIncomeCategoryNameByID(Long categoryId) {
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