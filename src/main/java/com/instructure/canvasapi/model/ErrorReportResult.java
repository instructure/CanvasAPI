package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class ErrorReportResult extends CanvasModel<ErrorReportResult> {
    private boolean logged;
    private long id;

    public boolean isLogged() {
        return logged;
    }

    public long getId() {
        return id;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(logged ? (byte) 1 : (byte) 0);
        dest.writeLong(this.id);
    }

    public ErrorReportResult() {
    }

    private ErrorReportResult(Parcel in) {
        this.logged = in.readByte() != 0;
        this.id = in.readLong();
    }

    public static final Parcelable.Creator<ErrorReportResult> CREATOR = new Parcelable.Creator<ErrorReportResult>() {
        public ErrorReportResult createFromParcel(Parcel source) {
            return new ErrorReportResult(source);
        }

        public ErrorReportResult[] newArray(int size) {
            return new ErrorReportResult[size];
        }
    };
}
