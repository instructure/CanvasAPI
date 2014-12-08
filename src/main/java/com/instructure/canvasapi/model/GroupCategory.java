package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class GroupCategory extends CanvasComparable<GroupCategory> {

    private long id;
    private String name;
    private String role;
    private String self_signup;
    private String context_type;
    //only one of these will be valid depending on the context type
    private long account_id;
    private long course_id;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSelf_signup() {
        return self_signup;
    }

    public void setSelf_signup(String self_signup) {
        this.self_signup = self_signup;
    }

    public String getContext_type() {
        return context_type;
    }

    public void setContext_type(String context_type) {
        this.context_type = context_type;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(long course_id) {
        this.course_id = course_id;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return name;
    }

    @Override
    public int compareTo(GroupCategory another) {
        return CanvasComparable.compare(id, another.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupCategory that = (GroupCategory) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.role);
        dest.writeString(this.self_signup);
        dest.writeString(this.context_type);
        dest.writeLong(this.account_id);
        dest.writeLong(this.course_id);
    }

    private GroupCategory(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.role = in.readString();
        this.self_signup = in.readString();
        this.context_type = in.readString();
        this.account_id = in.readLong();
        this.course_id = in.readLong();
    }

    public static Creator<GroupCategory> CREATOR = new Creator<GroupCategory>() {
        public GroupCategory createFromParcel(Parcel source) {
            return new GroupCategory(source);
        }

        public GroupCategory[] newArray(int size) {
            return new GroupCategory[size];
        }
    };
}
