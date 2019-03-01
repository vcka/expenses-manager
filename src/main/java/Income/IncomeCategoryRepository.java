package Income;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import MoneyFlow.Repository;
import Util.Constants;
import MoneyFlow.MoneyFlowCategory;

public class IncomeCategoryRepository implements Repository {
    private List<MoneyFlowCategory> moneyFlowCategoryList = new ArrayList<>();
    private static IncomeCategoryRepository incomeCategoryRepository;

    private IncomeCategoryRepository() {
    }

    public static IncomeCategoryRepository getRepository() {
        if (incomeCategoryRepository == null) {
            incomeCategoryRepository = new IncomeCategoryRepository() {
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
        FileOutputStream fos = new FileOutputStream(Constants.INCOME_CATEGORIES_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowCategoryList);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(Constants.INCOME_CATEGORIES_DB);
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlowCategory>) ois.readObject());
        ois.close();
    }
}
