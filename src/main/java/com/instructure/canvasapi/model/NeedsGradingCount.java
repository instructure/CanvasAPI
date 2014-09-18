package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Hoa Hoang
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class NeedsGradingCount implements Parcelable, Serializable, Comparable<NeedsGradingCount>{

    public static final long serialVersionUID = 1L;

    private long section_id;
    private long needs_grading_count;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
    public long getSectionId() {
        return section_id;
    }
    public void setSectionId(long section_id) {
        this.section_id = section_id;
    }
    public long getNeedsGradingCount() {
        return needs_grading_count;
    }
    public void setNeedsGradingCount(long needs_grading_count) {
        this.needs_grading_count = needs_grading_count;
    }

    @Override
    public int compareTo(NeedsGradingCount needsGradingCount) {
        return String.valueOf(this.getSectionId()).compareTo(String.valueOf(needsGradingCount.getSectionId()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.section_id);
        parcel.writeLong(this.needs_grading_count);
    }

    public NeedsGradingCount(){}

    public NeedsGradingCount(Parcel in){
        this.section_id = in.readLong();
        this.needs_grading_count = in.readLong();
    }

    public static Creator<NeedsGradingCount> CREATOR = new Creator<NeedsGradingCount>() {
        @Override
        public NeedsGradingCount createFromParcel(Parcel parcel) {
            return new NeedsGradingCount(parcel);
        }

        @Override
        public NeedsGradingCount[] newArray(int i) {
            return new NeedsGradingCount[i];
        }
    };
}
