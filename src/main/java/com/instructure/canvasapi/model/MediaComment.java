package com.instructure.canvasapi.model;

import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.utilities.FileUtilities;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class MediaComment extends CanvasComparable<MediaComment> implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum MediaType { AUDIO, VIDEO }

    //TODO: handle thumbnail of video?

    private String media_id;
    private String display_name;
    private String url;
    private String media_type;

    @SerializedName("content-type")
    private String content_type;

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    public String getMediaId() {
        return media_id;
    }
    public String getDisplayName(Date created_at) {
        if(display_name == null || display_name.equals("null"))
            return created_at.toLocaleString() + "." + FileUtilities.getFileExtensionFromMimetype(content_type);
        else
            return display_name;
    }
    public String getUrl() {
        return url;
    }
    public MediaType getMediaType() {
        if("video".equals(media_type)) {
            return MediaType.VIDEO;
        } else {
           return MediaType.AUDIO;
        }
    }
    public String getMimeType() {
        return content_type;
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
        return display_name;
    }
}
