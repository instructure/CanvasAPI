package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class DiscussionTopicPermission implements Parcelable {

	private static final long serialVersionUID = 1L;

	private boolean attach = false;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public boolean canAttach() {
		return attach;
	}
	public void setCanAttach(boolean can_attach) {
		this.attach = can_attach;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(attach ? (byte) 1 : (byte) 0);
    }

    public DiscussionTopicPermission() {
    }

    private DiscussionTopicPermission(Parcel in) {
        this.attach = in.readByte() != 0;
    }

    public static Creator<DiscussionTopicPermission> CREATOR = new Creator<DiscussionTopicPermission>() {
        public DiscussionTopicPermission createFromParcel(Parcel source) {
            return new DiscussionTopicPermission(source);
        }

        public DiscussionTopicPermission[] newArray(int size) {
            return new DiscussionTopicPermission[size];
        }
    };
}
