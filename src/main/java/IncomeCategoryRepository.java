import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeCategoryRepository implements RepositoryService {
    private static List<IncomeCategory> incomeCategoryList = new ArrayList<>();
    private static IncomeCategoryRepository incomeCategoryRepository;

    private IncomeCategoryRepository() {
    }

    static IncomeCategoryRepository getIncomeCategoryRepository() {
        if (incomeCategoryRepository == null) {
            incomeCategoryRepository = new IncomeCategoryRepository() {
            };
        }
        return incomeCategoryRepository;
    }

    List<IncomeCategory> getIncomeCategoryList() {
        return incomeCategoryList;
    }

    private void setIncomeCategoryList(List<IncomeCategory> incomeCategoryList) {
        IncomeCategoryRepository.incomeCategoryList = incomeCategoryList;
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
        setIncomeCategoryList((List<IncomeCategory>) ois.readObject());
        ois.close();
    }
}
