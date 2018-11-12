package io.mova.kucherenko.gettyimages.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

    public static final String YYYY_MM_DD_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DD_MMMM_YYYY = "dd MMMM yyyy";
    public static final String HH = "HH";
    public static final String HH_mm_ss = "HH:mm:ss";

    public static String formatOrderDateTimeForItem(String date, String pattern, String patternInput) {

        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(patternInput, Locale.US);
            try {
                Date parsedDate = sdf.parse(date);
                SimpleDateFormat sdfValidDate = new SimpleDateFormat(pattern, Locale.US);
                return sdfValidDate.format(parsedDate);
            } catch (ParseException e) {
                return "---";
            }
        } else {
            return "---";
        }
    }

    public static String getFormatedDateNow() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_mm_SS, Locale.US);
        return dateFormat.format(date);
    }

}