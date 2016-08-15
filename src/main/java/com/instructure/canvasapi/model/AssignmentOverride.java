package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class AssignmentOverride extends CanvasModel<AssignmentOverride> {

    public long id;
    @SerializedName("assignment_id")
    public long assignmentId;
    public String title;
    @SerializedName("due_at")
    public Date dueAt;
    @SerializedName("all_day")
    boolean allDay;
    @SerializedName("all_day_date")
    public String allDayDate;
    @SerializedName("unlock_at")
    public Date unlockAt;
    @SerializedName("lock_at")
    public Date lockAt;
    @SerializedName("course_section_id")
    public long courseSectionId;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Date getComparisonDate() {
        return dueAt;
    }

    @Override
    public String getComparisonString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.assignmentId);
        dest.writeString(this.title);
        dest.writeLong(this.dueAt != null ? this.dueAt.getTime() : -1);
        dest.writeByte(this.allDay ? (byte) 1 : (byte) 0);
        dest.writeString(this.allDayDate);
        dest.writeLong(this.unlockAt != null ? this.unlockAt.getTime() : -1);
        dest.writeLong(this.lockAt != null ? this.lockAt.getTime() : -1);
        dest.writeLong(this.courseSectionId);
    }

    public AssignmentOverride() {
    }

    protected AssignmentOverride(Parcel in) {
        this.id = in.readLong();
        this.assignmentId = in.readLong();
        this.title = in.readString();
        long tmpDueAt = in.readLong();
        this.dueAt = tmpDueAt == -1 ? null : new Date(tmpDueAt);
        this.allDay = in.readByte() != 0;
        this.allDayDate = in.readString();
        long tmpUnlockAt = in.readLong();
        this.unlockAt = tmpUnlockAt == -1 ? null : new Date(tmpUnlockAt);
        long tmpLockAt = in.readLong();
        this.lockAt = tmpLockAt == -1 ? null : new Date(tmpLockAt);
        this.courseSectionId = in.readLong();
    }

    public static final Parcelable.Creator<AssignmentOverride> CREATOR = new Parcelable.Creator<AssignmentOverride>() {
        @Override
        public AssignmentOverride createFromParcel(Parcel source) {
            return new AssignmentOverride(source);
        }

        @Override
        public AssignmentOverride[] newArray(int size) {
            return new AssignmentOverride[size];
        }
    };
}
