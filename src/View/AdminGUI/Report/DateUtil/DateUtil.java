package View.AdminGUI.Report.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Didoy on 1/14/2016.
 */
public class DateUtil {

    public static int getYear(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        return year;
    }
    public static int getMonth(){
        Date date= new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        return month+1;
    }
}
