package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class ParentResponse extends CanvasModel<ParentResponse> {

    private String token;

    @SerializedName("parent_id")
    private String parentId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public long getId() {
        return token.hashCode();
    }

    public String getParentId() {
        return parentId;
    }
    public void setId(String id) {
        this.parentId = id;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.token);
        dest.writeString(this.parentId);
    }

    public ParentResponse() {
    }

    protected ParentResponse(Parcel in) {
        this.token = in.readString();
        this.parentId = in.readString();
    }

    public static final Creator<ParentResponse> CREATOR = new Creator<ParentResponse>() {
        @Override
        public ParentResponse createFromParcel(Parcel source) {
            return new ParentResponse(source);
        }

        @Override
        public ParentResponse[] newArray(int size) {
            return new ParentResponse[size];
        }
    };
}
