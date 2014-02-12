package com.instructure.canvasapi.model;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Attachment extends CanvasModel<Attachment> {

    private long id;
    private String display_name;

    @SerializedName("content-type")
    private String content_type;
    private String filename;
    private String url;
    private String thumbnail_url;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId(){
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDisplayName() {
        return display_name;
    }
    public String getMimeType() {
        return content_type;
    }
    public String getFilename() {
        return filename;
    }
    public String getUrl() {
        return url;
    }
    public String getThumbnailUrl() {
        return thumbnail_url;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getDisplayName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Attachment() {}
}
