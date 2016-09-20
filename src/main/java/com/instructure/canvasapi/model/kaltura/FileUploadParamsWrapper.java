package com.instructure.canvasapi.model.kaltura;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.model.CanvasModel;
import com.instructure.canvasapi.model.FileUploadParams;

import java.util.Date;
import java.util.List;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */

public class FileUploadParamsWrapper extends CanvasModel<FileUploadParamsWrapper> {

    @SerializedName("attachments")
    private List<FileUploadParams> uploadParams;

    public List<FileUploadParams> getUploadParams() {
        return uploadParams;
    }

    public void setUploadParams(List<FileUploadParams> uploadParams) {
        this.uploadParams = uploadParams;
    }

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

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    /////////////////////////////////////////////////////////////////////////
    // Constructors
    /////////////////////////////////////////////////////////////////////////
    public FileUploadParamsWrapper() {}

    private FileUploadParamsWrapper(Parcel in) {
        this.uploadParams = (List<FileUploadParams>)in.readSerializable();

    }

    public static final Creator<FileUploadParamsWrapper> CREATOR = new Creator<FileUploadParamsWrapper>() {
        public FileUploadParamsWrapper createFromParcel(Parcel source) {
            return new FileUploadParamsWrapper(source);
        }

        public FileUploadParamsWrapper[] newArray(int size) {
            return new FileUploadParamsWrapper[size];
        }
    };

}
