package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

/**
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class Favorite extends CanvasComparable<Favorite> implements android.os.Parcelable {

    private long context_id;
    private String context_type;

    public long getContext_id() {
        return context_id;
    }

    public void setContext_id(long context_id) {
        this.context_id = context_id;
    }

    public String getContext_type() {
        return context_type;
    }

    public void setContext_type(String context_type) {
        this.context_type = context_type;
    }

    @Override
    public long getId() {
        return super.getId();
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
        dest.writeLong(this.context_id);
        dest.writeString(this.context_type);
    }

    private Favorite(Parcel in) {
        this.context_id = in.readLong();
        this.context_type = in.readString();
    }

    public static Creator<Favorite> CREATOR = new Creator<Favorite>() {
        public Favorite createFromParcel(Parcel source) {
            return new Favorite(source);
        }

        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
}
