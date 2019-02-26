import MoneyFlow.MoneyFlowCategory;
import Service.PEMServiceHelper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import Application.DateUtil;

class DateUtilTest {
    SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
    PEMServiceHelper pemService = new PEMServiceHelper();

    DateUtilTest() throws IOException, InterruptedException {
    }

    @Test
    void dateToStringTest() throws ParseException {
        assertEquals("2019 02 01", DateUtil.dateToString(df.parse("2019 02 01")));
    }

    @Test
    void stringToDateTest() throws ParseException {
        assertEquals(df.parse("2019 01 01"), DateUtil.stringToDate("2019 01 01"));
    }

    @Test
    void checkInputForMenuTest() {
        assertEquals(5, pemService.checkInput("5"));
    }

    @Test
    void parseToDoubleTest(){
        assertEquals(10.3, pemService.parseToDouble("10.3"));
    }

    @Test
    void calculateMonthlyTotalTest(){
        MoneyFlowCategory cat = new MoneyFlowCategory("TestCategory");
//        Repository repo = Repository.getRepository();
//        repo.getCategoryList().add(cat);
        assertEquals("TestCategory", cat.getName());
    }
}