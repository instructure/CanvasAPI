package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class User extends CanvasContext{

    private long id;
    private String name;
    private String short_name;
    private String login_id;
    private String avatar_url;
    private String primary_email;

    private List<Enrollment> enrollments;

    //Helper variable for the "specified" enrollment.
    private int enrollmentIndex;

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

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

    @Override
    public Type getType() {
        return Type.USER;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getShortName() {
        return short_name;
    }
    public void setShortName(String shortName) {
        this.short_name = shortName;
    }
    public String getLoginId() {
        return login_id;
    }
    public void setLoginId(String loginId) {
        this.login_id = loginId;
    }
    public String getAvatarURL() {
        return avatar_url;
    }
    public void setAvatarURL(String avatar) {
        this.avatar_url = avatar;
    }
    public String getEmail() {
        return primary_email;
    }
    public void setEmail(String email) {
        this.primary_email = email;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
    public void setEnrollments(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }
    public int getEnrollmentIndex(){
        return enrollmentIndex;
    }
    public void setEnrollmentIndex(int index){
        enrollmentIndex = index;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public User() {}

    public User(long id) {
        this.id = id;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.short_name);
        dest.writeString(this.login_id);
        dest.writeString(this.avatar_url);
        dest.writeString(this.primary_email);
        dest.writeList(this.enrollments);
        dest.writeInt(this.enrollmentIndex);
    }

    private User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.short_name = in.readString();
        this.login_id = in.readString();
        this.avatar_url = in.readString();
        this.primary_email = in.readString();

        this.enrollments = new ArrayList<Enrollment>();
        in.readList(this.enrollments, Enrollment.class.getClassLoader());

        this.enrollmentIndex = in.readInt();
    }

    public static Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int compareTo(CanvasContext canvasContext) {
        return 0;
    }
}
