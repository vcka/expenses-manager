import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeIRepository implements IRepository {
    private List<MoneyFlow> incomeList = new ArrayList<>();
    private static IncomeIRepository incomeRepository;

    private IncomeIRepository() {
    }

    static IncomeIRepository getRepository() {
        if (incomeRepository == null) {
            incomeRepository = new IncomeIRepository() {
            };
        }
        return incomeRepository;
    }

    @Override
    public List<MoneyFlow> getList() {
        return this.incomeList;
    }

    @Override
    public void setList(List list) {
        this.incomeList = list;
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
        setList((List<MoneyFlow>) ois.readObject());
        ois.close();
    }
}
