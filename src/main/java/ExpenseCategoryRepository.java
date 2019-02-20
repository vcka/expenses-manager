import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseCategoryRepository implements RepositoryService {
    private static List<Category> categoryList = new ArrayList<>();
    private static ExpenseCategoryRepository expenseCategoryRepository;

    private ExpenseCategoryRepository() {
    }

    static ExpenseCategoryRepository getExpenseCategoryRepository() {
        if (expenseCategoryRepository == null) {
            expenseCategoryRepository = new ExpenseCategoryRepository() {
            };
        }
        return expenseCategoryRepository;
    }

    List<Category> getCategoryList() {
        return categoryList;
    }

    private void setExpenseList(List<Category> categoryList) {
        ExpenseCategoryRepository.categoryList = categoryList;
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
        setExpenseList((List<Category>) ois.readObject());
        ois.close();
    }
}
