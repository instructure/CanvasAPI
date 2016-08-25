package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class AssignmentSet extends CanvasModel<AssignmentSet> {

    private long id;
    @SerializedName("scoring_range_id")
    private long scoringRangeId;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    private int position;
    private MasteryPathAssignment[] assignments;


    public void setId(long id) {
        this.id = id;
    }

    public long getScoringRangeId() {
        return scoringRangeId;
    }

    public void setScoringRangeId(long scoringRangeId) {
        this.scoringRangeId = scoringRangeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MasteryPathAssignment[] getAssignments() {
        return assignments;
    }

    public void setAssignments(MasteryPathAssignment[] assignments) {
        this.assignments = assignments;
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
        return id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.scoringRangeId);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeInt(this.position);
        dest.writeTypedArray(this.assignments, flags);
    }

    public AssignmentSet() {
    }

    protected AssignmentSet(Parcel in) {
        this.id = in.readLong();
        this.scoringRangeId = in.readLong();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.position = in.readInt();
        this.assignments = in.createTypedArray(MasteryPathAssignment.CREATOR);
    }

    public static final Creator<AssignmentSet> CREATOR = new Creator<AssignmentSet>() {
        @Override
        public AssignmentSet createFromParcel(Parcel source) {
            return new AssignmentSet(source);
        }

        @Override
        public AssignmentSet[] newArray(int size) {
            return new AssignmentSet[size];
        }
    };
}
