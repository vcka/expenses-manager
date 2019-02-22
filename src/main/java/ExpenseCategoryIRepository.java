import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryIRepository implements IRepository {
    private List<Category> categoryList = new ArrayList<>();
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
    public List<Category> getList() {
        return categoryList;
    }

    @Override
    public void setList(List list) {
        this.categoryList = list;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Categories.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(categoryList);
        oos.close();
    }

    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Categories.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<Category>) ois.readObject());
        ois.close();
    }
}
