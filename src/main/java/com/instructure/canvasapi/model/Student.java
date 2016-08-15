package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class Student extends CanvasModel<Student> {

    @SerializedName("parent_id")
    private String parentId;

    @SerializedName("student_id")
    private String studentId;

    @SerializedName("student_name")
    private String studentName;

    @SerializedName("student_domain")
    private String studentDomain;

    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentDomain() {
        return studentDomain;
    }

    public void setStudentDomain(String studentDomain) {
        this.studentDomain = studentDomain;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String getComparisonString() {
        return null;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public long getId() {
        return studentId.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.parentId);
        dest.writeString(this.studentId);
        dest.writeString(this.studentName);
        dest.writeString(this.studentDomain);
        dest.writeString(this.avatarUrl);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.parentId = in.readString();
        this.studentId = in.readString();
        this.studentName = in.readString();
        this.studentDomain = in.readString();
        this.avatarUrl = in.readString();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
