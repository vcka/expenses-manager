package Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date stringToDate(String dateAsString){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
        try {
            return df.parse(dateAsString);
        } catch (ParseException e) {
            System.out.print("Wrong date format. Try again: ");
            return null;
        }
    }
    public static String dateToString(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM dd");
        return df.format(date);

    }
    public static String getYearAndMonth(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy MM");
        return df.format(date);
    }
    public static Integer getYear(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy");
        return new Integer(df.format(date));
    }
}
