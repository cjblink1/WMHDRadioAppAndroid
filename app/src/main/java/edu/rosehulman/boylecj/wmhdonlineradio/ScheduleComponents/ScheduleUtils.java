package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Connor on 2/12/2017.
 */

public class ScheduleUtils {

    public static String readableTime(String input){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date timeStart = sdf.parse(input, new ParsePosition(0));
        if (timeStart == null) {
            return "Could not parse.";
        }
        cal.setTime(timeStart);
        cal.setTimeZone(TimeZone.getDefault());

        StringBuilder sb = new StringBuilder();
        appendTime(sb, cal);

        if (!TimeZone.getDefault().equals(TimeZone.getTimeZone("EDT")))
        {
            sb.append(" EST ");
            sb.append("(");
            cal.setTimeZone(TimeZone.getTimeZone("EDT"));
            appendTime(sb, cal);
            sb.append(" "+TimeZone.getDefault().getDisplayName(true, TimeZone.SHORT));
            sb.append(")");
        }

        return sb.toString();
    }

    private static void appendTime(StringBuilder sb, Calendar cal){
        int hour = cal.get(Calendar.HOUR);
        sb.append(hour == 0 ? 12 : hour);
        sb.append(":");
        sb.append(String.format("%02d",cal.get(Calendar.MINUTE)));
        if(cal.get(Calendar.AM_PM)== 0){
            sb.append("am");
        } else{
            sb.append("pm");
        }
    }

}
