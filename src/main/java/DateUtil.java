import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class DateUtil {
    static Date stringToDate(String dateAsString){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
        try {
            return df.parse(dateAsString);
        } catch (ParseException e) {
            System.out.print("Wrong date format. Try again: ");
            return null;
        }
    }
    static String dateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
        return df.format(date);

    }
    static String getYearAndMonth(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM");
        return df.format(date);
    }
    static Integer getYear(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return new Integer(df.format(date));
    }
}
