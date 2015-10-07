package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class DiscussionTopicPermission extends CanvasModel<DiscussionTopicPermission> {

	private static final long serialVersionUID = 1L;

	private boolean attach = false;
    private boolean update = false;
    private boolean delete = false;
    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public boolean canAttach() {
		return attach;
	}
	public void setCanAttach(boolean can_attach) {
		this.attach = can_attach;
	}

    public boolean canUpdate(){
        return update;
    }
    public void setCanUpdate(boolean update){
        this.update = update;
    }

    public boolean canDelete(){
        return delete;
    }
    public void setCanDelete(boolean delete){
        this.delete = delete;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(attach ? (byte) 1 : (byte) 0);
        dest.writeByte(update ? (byte) 1 : (byte) 0);
        dest.writeByte(delete ? (byte) 1 : (byte) 0);
    }

    public DiscussionTopicPermission() {}

    private DiscussionTopicPermission(Parcel in) {
        this.attach = in.readByte() != 0;
        this.update = in.readByte() != 0;
        this.delete = in.readByte() != 0;
    }

    public static Creator<DiscussionTopicPermission> CREATOR = new Creator<DiscussionTopicPermission>() {
        public DiscussionTopicPermission createFromParcel(Parcel source) {
            return new DiscussionTopicPermission(source);
        }

        public DiscussionTopicPermission[] newArray(int size) {
            return new DiscussionTopicPermission[size];
        }
    };

    @Override
    public long getId() {
        return 0;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return null;
    }
}
