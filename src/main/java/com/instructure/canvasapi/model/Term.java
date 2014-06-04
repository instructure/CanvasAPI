package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * Created by Joshua Dutton on 9/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class Term extends CanvasModel<Term>{

    //currently only part of a course
	/*
	    term: {
		    id: 1,
		    name: 'Default Term',
		    start_at: "2012-06-01T00:00:00-06:00",
		    end_at: null
	    }
	 */

    // Variables from API
    private long id;
    private String name;
    private String start_at;
    private String end_at;

    // Helper variables
    private Date startDate;
    private Date endDate;
    private boolean isGroupTerm = false;

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Date getStartAt() {
        if (startDate == null) {
            startDate = APIHelpers.stringToDate(start_at);
        }
        return startDate;
    }
    public Date getEndAt() {
        if (endDate == null) {
            endDate = APIHelpers.stringToDate(end_at);
        }
        return endDate;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return getStartAt();
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Term() {}

    public Term(String name) {
        this.name = name;
    }

    public Term(boolean isGroupTerm, String name) {
        id = Long.MAX_VALUE;
        this.startDate = null;
        this.name = name;

        this.isGroupTerm = isGroupTerm;
    }

    @Override
    public int compareTo(Term term) {

        if (isGroupTerm && term.isGroupTerm) {
            return 0;
        } else if (isGroupTerm) {
            return 1;
        } else if (term.isGroupTerm) {
            return -1;
        }

        return ((CanvasComparable)this).compareTo(term);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.start_at);
        dest.writeString(this.end_at);
        dest.writeLong(startDate != null ? startDate.getTime() : -1);
        dest.writeLong(endDate != null ? endDate.getTime() : -1);
        dest.writeByte(isGroupTerm ? (byte) 1 : (byte) 0);
    }

    private Term(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.start_at = in.readString();
        this.end_at = in.readString();
        long tmpStartDate = in.readLong();
        this.startDate = tmpStartDate == -1 ? null : new Date(tmpStartDate);
        long tmpEndDate = in.readLong();
        this.endDate = tmpEndDate == -1 ? null : new Date(tmpEndDate);
        this.isGroupTerm = in.readByte() != 0;
    }

    public static Creator<Term> CREATOR = new Creator<Term>() {
        public Term createFromParcel(Parcel source) {
            return new Term(source);
        }

        public Term[] newArray(int size) {
            return new Term[size];
        }
    };
}
