package com.instructure.canvasapi.utilities;

import android.content.Context;
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

    public static String createDateString(final Date date) {
        if (date == null){
            return null;
        }

        return new SimpleDateFormat("MMMM d, yyyy", Locale.US).format(date);
    }

    public static String createShortDateString(final Date date) {
        if (date == null){
            return null;
        }

        return new SimpleDateFormat("MMM d, yyyy", Locale.US).format(date);
    }

    public static String createDateTimeString(Context context, String prefix, Date date) {
        return prefix + ": " +
                DateFormat.format("MMM d, yyyy", date) + " " +
                context.getString(R.string.at) + " " +
                createTimeString(date);
    }

    public static String createShortDateTimeString(Context context, Date date) {
        return DateFormat.format("MMM d", date) + " " +
                context.getString(R.string.at) + " " +
                createTimeString(date);
    }

    public static String createTimeString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.MINUTE) == 0) {
            return DateFormat.format("haa", calendar).toString().toLowerCase();
        }
        return DateFormat.format("h:mmaa", calendar).toString().toLowerCase();
    }

}
