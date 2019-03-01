package Expense;

import Util.Constants;
import MoneyFlow.Repository;
import MoneyFlow.MoneyFlow;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExpenseRepository implements Repository {
    private List<MoneyFlow> moneyFlowList = new ArrayList<>();
    private static ExpenseRepository expenseRepository;

    private ExpenseRepository() {
    }

    public static ExpenseRepository getRepository() {
        if (expenseRepository == null) {
            expenseRepository = new ExpenseRepository() {
            };
        }
        return expenseRepository;
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
        FileOutputStream fos = new FileOutputStream(Constants.EXPENSES_DB);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(moneyFlowList);
        oos.close();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void dataLoad() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(Constants.EXPENSES_DB);
        ObjectInputStream ois = new ObjectInputStream(fis);
        setList((List<MoneyFlow>) ois.readObject());
        ois.close();
    }
}
