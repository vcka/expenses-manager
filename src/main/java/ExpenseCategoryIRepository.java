import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryIRepository implements IRepository {
    private List<MoneyFlowCategory> moneyFlowCategoryList = new ArrayList<>();
    private static ExpenseCategoryIRepository expenseCategoryRepository;

    private ExpenseCategoryIRepository() {
    }

    static ExpenseCategoryIRepository getRepository() {
        if (expenseCategoryRepository == null) {
            expenseCategoryRepository = new ExpenseCategoryIRepository() {
            };
        }
        return expenseCategoryRepository;
    }

    @Override
    public List<MoneyFlowCategory> getList() {
        return moneyFlowCategoryList;
    }

    @Override
    public void setList(List list) {
        this.moneyFlowCategoryList = list;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Categories.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowCategoryList);
        oos.close();
    }

    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Categories.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlowCategory>) ois.readObject());
        ois.close();
    }
}
