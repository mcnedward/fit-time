package com.mcnedward.fittime.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Extension class for getting formatted dates.
 * Created by Edward on 7/12/2017.
 */

public class Dates {
    public static String PRETTY_DATE = "dd/MM/yyyy";
    public static String DATABASE_DATE = "yyyy-MM-dd";
    public static String DATABASE_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String NUMBER_DATE = "yyyyMMdd";
    public static Locale US = Locale.US;

    /**
     * Gets a date stamp in the database format (yyyyy-MM-dd).
     *
     * @return The date stamp in database format.
     */
    public static String getDatabaseDateStamp() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATABASE_DATE, US);
        return simpleDateFormat.format(date);
    }

    /**
     * Gets a date time stamp in the database format (yyyyy-MM-dd).
     *
     * @return The date time stamp in database format.
     */
    public static String getDatabaseDateTimeStamp() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATABASE_DATE_TIME, US);
        return simpleDateFormat.format(date);
    }

    /**
     * Get the specified date in the pretty date format (dd/MM/yyyy).
     *
     * @param date The date to convert to pretty date format.
     * @return The date.
     */
    public static String getCalendarPrettyDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PRETTY_DATE, US);
        return simpleDateFormat.format(date);
    }

    public static String getCalendarPrettyDate() {
        Date currentDate = getDate();
        return getCalendarPrettyDate(currentDate);
    }

    /**
     * Converts a database format (yyyy-MM-dd) date to a pretty format (dd/MM/yyyy) date.
     *
     * @param date The date to convert.
     * @return The date in pretty format.
     */
    public static String getPrettyDateFromDatabaseDate(String date) {
        return convertDate(date, DATABASE_DATE, PRETTY_DATE);
    }

    /**
     * Converts a pretty format (dd/MM/yyyy) date to a database format (yyyy-MM-dd) date.
     *
     * @param date The date to convert.
     * @return The date in database format.
     */
    public static String getDatabaseDateFromPrettyDate(String date) {
        return convertDate(date, PRETTY_DATE, DATABASE_DATE);
    }

    public static String getDateFromRange(int dateRange, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1 * dateRange);
        Date previousDate = calendar.getTime();
        return new SimpleDateFormat(format, US).format(previousDate);
    }

    public static String getNumberDateStamp() {
        Date date = getDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NUMBER_DATE, US);
        return simpleDateFormat.format(date);
    }

    public static int getDateAsNumber(String date) {
        String datestamp = convertDate(date, DATABASE_DATE, NUMBER_DATE);
        return Integer.parseInt(datestamp);
    }

    public static String getDateFromNumber(double date) {
        String datestamp = convertDate(String.valueOf(date), NUMBER_DATE, PRETTY_DATE);
        return datestamp;
    }

    private static Date getDate() {
        return new Date();
    }

    public static String convertDate(String date, String fromFormat, String toFormat) {
        SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat, US);
        String d = null;
        try {
            Date theDate = fromDateFormat.parse(date);
            SimpleDateFormat toDateFormat = new SimpleDateFormat(toFormat, US);
            d = toDateFormat.format(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // TODO Handle this
        }
        return d;
    }
}
