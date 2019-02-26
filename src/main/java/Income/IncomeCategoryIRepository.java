package Income;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Application.Consts;
import MoneyFlow.IRepository;
import MoneyFlow.MoneyFlowCategory;

public class IncomeCategoryIRepository implements IRepository {
    private List<MoneyFlowCategory> moneyFlowCategoryList = new ArrayList<>();
    private static IncomeCategoryIRepository incomeCategoryRepository;

    private IncomeCategoryIRepository() {
    }

    public static IncomeCategoryIRepository getRepository() {
        if (incomeCategoryRepository == null) {
            incomeCategoryRepository = new IncomeCategoryIRepository() {
            };
        }
        return incomeCategoryRepository;
    }

    @Override
    public List<MoneyFlowCategory> getList() {
        return this.moneyFlowCategoryList;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setList(List list) {
        this.moneyFlowCategoryList = list;
    }

    @Override
    public void dataSave() throws IOException {
        FileOutputStream fos = new FileOutputStream(Consts.INCOME_CATEGORIES_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowCategoryList);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(Consts.INCOME_CATEGORIES_DB);
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlowCategory>) ois.readObject());
        ois.close();
    }
}
