package Income;

import Util.Constants;
import MoneyFlow.IRepository;
import MoneyFlow.MoneyFlow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeIRepository implements IRepository {

    private List<MoneyFlow> moneyFlowList = new ArrayList<>();
    private static IncomeIRepository incomeRepository;

    private IncomeIRepository() {
    }

    public static IncomeIRepository getRepository() {
        if (incomeRepository == null) {
            incomeRepository = new IncomeIRepository() {
            };
        }
        return incomeRepository;
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
        FileOutputStream fos = new FileOutputStream(Constants.INCOME_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowList);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(Constants.INCOME_DB);
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlow>) ois.readObject());
        ois.close();
    }
}
