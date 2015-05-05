package com.instructure.canvasapi.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joshua Dutton on 1/3/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public abstract class CanvasComparable<T extends CanvasComparable> implements Comparable<T>, Parcelable, Serializable {

    public static final long serialVersionUID = 1L;

    public long getId() {
        return -1;
    }
    // return null if there is no date
    public abstract Date getComparisonDate();
    public abstract String getComparisonString();

    ///////////////////////////////////////////////////////////////////////////
    // Comparisons
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public int compareTo(T comparable) {
        if (getId() == comparable.getId() && getId() > 0) {
            return 0;
        }

        int dateResult = CanvasComparable.<Date>compare(getComparisonDate(), comparable.getComparisonDate());
        if (dateResult != 0) {
            return dateResult;
        }

        int stringResult = CanvasComparable.<String>compare(getComparisonString(), comparable.getComparisonString());
        if (stringResult != 0) {
            return stringResult;
        }

        // even if they have the same date and string just compare ids
        return Long.valueOf(getId()).compareTo(comparable.getId());
    }

    public static <C extends Comparable> int compare(C a, C b) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return 1;
        } else if (b == null) {
            return -1;
        }
        return a.compareTo(b);
    }

    public static <C extends Comparable> boolean equals(C a, C b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null) {
            return false;
        } else if (b == null) {
            return false;
        }
        return a.equals(b);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
