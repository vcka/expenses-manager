import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeCategoryIRepository implements IRepository {
    private List<IncomeCategory> incomeCategoryList = new ArrayList<>();
    private static IncomeCategoryIRepository incomeCategoryRepository;

    private IncomeCategoryIRepository() {
    }

    static IncomeCategoryIRepository getRepository() {
        if (incomeCategoryRepository == null) {
            incomeCategoryRepository = new IncomeCategoryIRepository() {
            };
        }
        return incomeCategoryRepository;
    }

    @Override
    public List<IncomeCategory> getList() {
        return this.incomeCategoryList;
    }

    @Override
    public void setList(List list) {
        this.incomeCategoryList = list;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Income-Categories.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(incomeCategoryList);
        oos.close();
    }

    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Income-Categories.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<IncomeCategory>) ois.readObject());
        ois.close();
    }
}
