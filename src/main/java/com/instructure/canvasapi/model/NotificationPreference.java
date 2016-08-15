package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

public class NotificationPreference extends CanvasModel<NotificationPreference> {

    public String notification;
    public String category;
    public String frequency;

    @Override
    public long getId() {
        return 0;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.notification);
        dest.writeString(this.category);
        dest.writeString(this.frequency);
    }

    public NotificationPreference() {
    }

    private NotificationPreference(Parcel in) {
        this.notification = in.readString();
        this.category = in.readString();
        this.frequency = in.readString();
    }

    public static final Creator<NotificationPreference> CREATOR = new Creator<NotificationPreference>() {
        public NotificationPreference createFromParcel(Parcel source) {
            return new NotificationPreference(source);
        }

        public NotificationPreference[] newArray(int size) {
            return new NotificationPreference[size];
        }
    };
}
