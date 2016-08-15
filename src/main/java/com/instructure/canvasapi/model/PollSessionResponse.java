package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by brady on 5/27/14.
 */
public class PollSessionResponse extends CanvasComparable<PollSessionResponse> implements android.os.Parcelable {

    private List<PollSession> poll_sessions = new ArrayList<PollSession>();

    public List<PollSession> getPollSessions() {
        return poll_sessions;
    }

    public void setPollSessions(List<PollSession> pollSessions) {
        this.poll_sessions = pollSessions;
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
    public int compareTo(PollSessionResponse another) {
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(poll_sessions);
    }

    public PollSessionResponse() {
    }

    private PollSessionResponse(Parcel in) {
        in.readTypedList(poll_sessions, PollSession.CREATOR);
    }

    public static Creator<PollSessionResponse> CREATOR = new Creator<PollSessionResponse>() {
        public PollSessionResponse createFromParcel(Parcel source) {
            return new PollSessionResponse(source);
        }

        public PollSessionResponse[] newArray(int size) {
            return new PollSessionResponse[size];
        }
    };
}
