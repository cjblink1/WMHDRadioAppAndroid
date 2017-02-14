package edu.rosehulman.boylecj.wmhdonlineradio.ScheduleComponents;

import android.util.Log;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import edu.rosehulman.boylecj.wmhdonlineradio.Constants;

/**
 * Created by Connor on 2/12/2017.
 */

public class ScheduleUtils {

    public static String readableTime(String input){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ENGLISH);
        Date timeStart = sdf.parse(input+" -0500", new ParsePosition(0));
        if (timeStart == null) {
            return "Could not parse.";
        }
        cal.setTime(timeStart);

        StringBuilder sb = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("h:mm z", Locale.ENGLISH);
        sb.append(df.format(cal.getTime()));

        if (TimeZone.getDefault().getRawOffset() != TimeZone.getTimeZone("America/Indiana/Indianapolis").getRawOffset())
        {
            df.setTimeZone(TimeZone.getDefault());
            df.setTimeZone(TimeZone.getTimeZone("America/Indiana/Indianapolis"));
            sb.append(" ("+df.format(cal.getTime())+")");
        }

        return sb.toString();
    }

}
