package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class CustomColumn extends CanvasModel<CustomColumn> {

    private long id;
    private String title;           //header text
    private int position;           //column order
    private boolean hidden;         //won't be displayed if hidden is true
    private boolean teacher_notes;  //is it the teacher's note column?

    public String getTitle() {
        return title;
    }

    public int getPosition() {
        return position;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean isTeacher_notes() {
        return teacher_notes;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.position);
        dest.writeByte(hidden ? (byte) 1 : (byte) 0);
        dest.writeByte(teacher_notes ? (byte) 1 : (byte) 0);
    }

    private CustomColumn(Parcel in) {
        this.title = in.readString();
        this.position = in.readInt();
        this.hidden = in.readByte() != 0;
        this.teacher_notes = in.readByte() != 0;
    }

    public static final Creator<CustomColumn> CREATOR = new Creator<CustomColumn>() {
        public CustomColumn createFromParcel(Parcel source) {
            return new CustomColumn(source);
        }

        public CustomColumn[] newArray(int size) {
            return new CustomColumn[size];
        }
    };
}
