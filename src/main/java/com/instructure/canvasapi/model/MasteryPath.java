package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class MasteryPath extends CanvasModel<MasteryPath> {

    private boolean locked;

    @SerializedName("assignment_sets")
    private AssignmentSet[] assignmentSets;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public AssignmentSet[] getAssignmentSets() {
        return assignmentSets;
    }

    public void setAssignmentSets(AssignmentSet[] assignmentSets) {
        this.assignmentSets = assignmentSets;
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
    public long getId() {
        return 0;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.locked ? (byte) 1 : (byte) 0);
        dest.writeTypedArray(this.assignmentSets, flags);
    }

    public MasteryPath() {
    }

    protected MasteryPath(Parcel in) {
        this.locked = in.readByte() != 0;
        this.assignmentSets = in.createTypedArray(AssignmentSet.CREATOR);
    }

    public static final Creator<MasteryPath> CREATOR = new Creator<MasteryPath>() {
        @Override
        public MasteryPath createFromParcel(Parcel source) {
            return new MasteryPath(source);
        }

        @Override
        public MasteryPath[] newArray(int size) {
            return new MasteryPath[size];
        }
    };
}
