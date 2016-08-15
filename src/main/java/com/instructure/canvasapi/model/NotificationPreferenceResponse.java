package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationPreferenceResponse extends CanvasComparable<NotificationPreferenceResponse> {

    public List<NotificationPreference> notification_preferences = new ArrayList<>();

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
    public int compareTo(NotificationPreferenceResponse comparable) {
        return super.compareTo(comparable);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.notification_preferences);
    }

    public NotificationPreferenceResponse() {
    }

    private NotificationPreferenceResponse(Parcel in) {
        in.readTypedList(this.notification_preferences, NotificationPreference.CREATOR);
    }

    public static final Creator<NotificationPreferenceResponse> CREATOR = new Creator<NotificationPreferenceResponse>() {
        public NotificationPreferenceResponse createFromParcel(Parcel source) {
            return new NotificationPreferenceResponse(source);
        }

        public NotificationPreferenceResponse[] newArray(int size) {
            return new NotificationPreferenceResponse[size];
        }
    };
}
