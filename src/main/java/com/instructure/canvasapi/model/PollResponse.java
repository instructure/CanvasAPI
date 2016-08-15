package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class PollResponse extends CanvasComparable<PollResponse> {



    private List<Poll> polls = new ArrayList<Poll>();

    public List<Poll> getPolls() {
        return polls;
    }

    public void setPolls(List<Poll> polls) {
        this.polls = polls;
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
        dest.writeTypedList(polls);
    }

    public PollResponse() {
    }

    private PollResponse(Parcel in) {
        in.readTypedList(polls, Poll.CREATOR);
    }

    public static Creator<PollResponse> CREATOR = new Creator<PollResponse>() {
        public PollResponse createFromParcel(Parcel source) {
            return new PollResponse(source);
        }

        public PollResponse[] newArray(int size) {
            return new PollResponse[size];
        }
    };

    @Override
    public int compareTo(PollResponse another) {
        return 0;
    }
}
