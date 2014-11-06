package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UnreadNotificationCount implements Parcelable {

    private String type;
    private int count;
    private int unread_count;
    private String notification_category;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnreadCount() {
        return unread_count;
    }

    public void setUnreadCount(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getNotificationCategory() {
        return notification_category;
    }

    public void setNotificationCategory(String notification_category) {
        this.notification_category = notification_category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeInt(this.count);
        dest.writeInt(this.unread_count);
        dest.writeString(this.notification_category);
    }

    public UnreadNotificationCount() {
    }

    private UnreadNotificationCount(Parcel in) {
        this.type = in.readString();
        this.count = in.readInt();
        this.unread_count = in.readInt();
        this.notification_category = in.readString();
    }

    public static Creator<UnreadNotificationCount> CREATOR = new Creator<UnreadNotificationCount>() {
        public UnreadNotificationCount createFromParcel(Parcel source) {
            return new UnreadNotificationCount(source);
        }

        public UnreadNotificationCount[] newArray(int size) {
            return new UnreadNotificationCount[size];
        }
    };
}

