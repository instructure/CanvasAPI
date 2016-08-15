package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class CourseNickname extends CanvasComparable<CourseNickname> {

    @SerializedName("course_id")
    public long id;
    public String name;
    public String nickname;

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
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.nickname);
    }

    public CourseNickname() {
    }

    private CourseNickname(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.nickname = in.readString();
    }

    public static final Creator<CourseNickname> CREATOR = new Creator<CourseNickname>() {
        public CourseNickname createFromParcel(Parcel source) {
            return new CourseNickname(source);
        }

        public CourseNickname[] newArray(int size) {
            return new CourseNickname[size];
        }
    };
}
