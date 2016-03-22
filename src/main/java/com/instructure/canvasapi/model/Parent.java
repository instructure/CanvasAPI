package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

public class Parent extends CanvasModel<Parent> {

    private String user_full_name;
    private String username;
    private String token;
    private boolean tos_approved;

    public String getUserFullName() {
        return user_full_name;
    }

    public void setUserFullName(String user_full_name) {
        this.user_full_name = user_full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isTosApproved() {
        return tos_approved;
    }

    public void setTosApproved(boolean tos_approved) {
        this.tos_approved = tos_approved;
    }

    @Override
    public long getId() {
        return 0;
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
        dest.writeString(this.user_full_name);
        dest.writeString(this.username);
        dest.writeString(this.token);
        dest.writeByte(tos_approved ? (byte) 1 : (byte) 0);
    }

    public Parent() {
    }

    protected Parent(Parcel in) {
        this.user_full_name = in.readString();
        this.username = in.readString();
        this.token = in.readString();
        this.tos_approved = in.readByte() != 0;
    }

    public static final Creator<Parent> CREATOR = new Creator<Parent>() {
        public Parent createFromParcel(Parcel source) {
            return new Parent(source);
        }

        public Parent[] newArray(int size) {
            return new Parent[size];
        }
    };
}
