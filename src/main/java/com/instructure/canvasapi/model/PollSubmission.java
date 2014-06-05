package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class PollSubmission extends CanvasComparable<PollSubmission> implements Parcelable {

    private long id;
    private long poll_choice_id;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPoll_choice_id() {
        return poll_choice_id;
    }

    public void setPoll_choice_id(long poll_choice_id) {
        this.poll_choice_id = poll_choice_id;
    }


    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    public Date getComparisonDate() { return null; }
    public String getComparisonString() { return null; }

    @Override
    public int compareTo(PollSubmission pollSubmission) {
        return CanvasComparable.compare(this.getId(),pollSubmission.getId());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Parcelable
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.poll_choice_id);
    }

    public PollSubmission() {
    }

    private PollSubmission(Parcel in) {
        this.id = in.readLong();
        this.poll_choice_id = in.readLong();
    }

    public static Parcelable.Creator<PollSubmission> CREATOR = new Parcelable.Creator<PollSubmission>() {
        public PollSubmission createFromParcel(Parcel source) {
            return new PollSubmission(source);
        }

        public PollSubmission[] newArray(int size) {
            return new PollSubmission[size];
        }
    };
}
