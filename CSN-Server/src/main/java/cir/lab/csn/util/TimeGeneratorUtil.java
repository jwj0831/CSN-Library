package cir.lab.csn.util;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeGeneratorUtil {
    //static Logger logger = LoggerFactory.getLogger(TimeGeneratorUtil.class);
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    public static String getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String now = new SimpleDateFormat(DATE_FORMAT).format(date);
        return now;
    }

    public static long convertDateToEpoch(String date) {
        Date tempDate = null;
        try {
            tempDate = new SimpleDateFormat(DATE_FORMAT).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long epoch = tempDate.getTime();
        return epoch;
    }

    public static String convertEpochToDate(long epoch) {
        Date date = new Date(epoch);
        String now = new SimpleDateFormat(DATE_FORMAT).format(date);
        return now;
    }
}
