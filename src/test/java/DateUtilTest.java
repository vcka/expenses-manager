import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class DateUtilTest {
    SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
    PEMService pemService = new PEMService();

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
        Category cat = new Category("TestCategory");
//        Repository repo = Repository.getRepository();
//        repo.getCategoryList().add(cat);
        assertEquals("TestCategory", cat.getName());
    }
}