import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeRepository implements RepositoryService {
    private static List<Income> incomeList = new ArrayList<>();
    private static IncomeRepository incomeRepository;

    private IncomeRepository() {
    }

    static IncomeRepository getIncomeRepository() {
        if (incomeRepository == null) {
            incomeRepository = new IncomeRepository() {
            };
        }
        return incomeRepository;
    }

    List<Income> getIncomeList() {
        return incomeList;
    }

    private void setIncomeList(List<Income> incomeList) {
        IncomeRepository.incomeList = incomeList;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream("Income.db");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(incomeList);
        oos.close();
    }

    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Income.db");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setIncomeList((List<Income>) ois.readObject());
        ois.close();
    }
}
