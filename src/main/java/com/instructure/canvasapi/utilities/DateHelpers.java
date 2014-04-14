package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.instructure.canvasapi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Joshua Dutton on 1/15/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DateHelpers {

    public enum DATE_FORMAT {MEDIUM, LONG}



    public static String createDateString(final Date date) {
        if (date == null){
            return null;
        }

        return new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(date);
    }

    public static String createShortDateString(final Date date) {
        if (date == null){
            return null;
        }

        return new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(date);
    }

    public static String createDateTimeString(Context context, String prefix, Date date) {
        return prefix + ": " +
                DateHelpers.getDateToDayMonthYearDateFormat().format(date) + " " +
                context.getString(R.string.at) + " " +
                createTimeString(context, date);
    }

    public static String createShortDateTimeString(Context context, Date date) {
        return DateHelpers.getDateToShortDayMonthDateFormat().format(date) + " " +
                context.getString(R.string.at) + " " +
                createTimeString(context, date);
    }

    public static String createTimeString(Context context, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.MINUTE) == 0) {
            return DateHelpers.getPreferredTimeFormatHourOnly(context).format(date).toString();
        }

        return DateHelpers.getPreferredTimeFormat(context).format(date).toString();
    }

    public static SimpleDateFormat getPreferredTimeFormat(Context context) {
        if(DateFormat.is24HourFormat(context)) {
            return new SimpleDateFormat("HH:mm", Locale.getDefault());
        }
        return new SimpleDateFormat("hh:mm a", Locale.getDefault());
    }

    public static SimpleDateFormat getPreferredTimeFormatHourOnly(Context context) {
        if(DateFormat.is24HourFormat(context)) {
            return new SimpleDateFormat("HH:mm", Locale.getDefault());
        }
        return new SimpleDateFormat("h aa", Locale.getDefault());
    }

    public static java.text.DateFormat getPreferredNumericalDateFormat(Context context, DATE_FORMAT dateFormat) {
        final String format = Settings.System.getString(context.getContentResolver(), Settings.System.DATE_FORMAT);
        if (TextUtils.isEmpty(format)) {
            if(dateFormat == DATE_FORMAT.LONG) {
                return android.text.format.DateFormat.getLongDateFormat(context);
            } else {
                return android.text.format.DateFormat.getMediumDateFormat(context);
            }
        } else {
            return new SimpleDateFormat(format);
        }
    }

    public static SimpleDateFormat getDateToDayMonthYearDateFormat(){
        return new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
    }

    public static SimpleDateFormat getDateToShortDayMonthDateFormat(){
        return new SimpleDateFormat("MMM d", Locale.getDefault());
    }

    public static SimpleDateFormat getDateToDayMonthDateFormat(){
        return new SimpleDateFormat("MMM dd", Locale.getDefault());
    }

    public static SimpleDateFormat getDateToMonthDayYearHourMinute(Context context){
        if(DateFormat.is24HourFormat(context)) {
            return new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());
        }
        return new SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault());
    }

    public static SimpleDateFormat getMonthFormat() {
        return new SimpleDateFormat("MMM", Locale.getDefault());
    }
}
