import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseIRepository implements IRepository {
    private List<MoneyFlow> moneyFlowList = new ArrayList<>();
    private static ExpenseIRepository expenseRepository;

    private ExpenseIRepository() {
    }

    static ExpenseIRepository getRepository() {
        if (expenseRepository == null) {
            expenseRepository = new ExpenseIRepository() {
            };
        }
        return expenseRepository;
    }

    @Override
    public List<MoneyFlow> getList() {
        return this.moneyFlowList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setList(List list) {
        this.moneyFlowList = list;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Expenses.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowList);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Expenses.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlow>) ois.readObject());
        ois.close();
    }
}
