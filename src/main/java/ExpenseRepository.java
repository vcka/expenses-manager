import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository implements RepositoryService {
    private static List<Expense> expenseList = new ArrayList<>();
    private static ExpenseRepository expenseRepository;

    private ExpenseRepository() {
    }

    static ExpenseRepository getExpenseRepository() {
        if (expenseRepository == null) {
            expenseRepository = new ExpenseRepository() {
            };
        }
        return expenseRepository;
    }

    List<Expense> getExpenseList() {
        return expenseList;
    }

    private void setExpenseList(List<Expense> expenseList) {
        ExpenseRepository.expenseList = expenseList;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Expenses.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(expenseList);
        oos.close();
    }

    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Expenses.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setExpenseList((List<Expense>) ois.readObject());
        ois.close();
    }
}
