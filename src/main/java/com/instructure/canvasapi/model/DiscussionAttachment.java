package com.instructure.canvasapi.model;

import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * Created by Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DiscussionAttachment extends CanvasModel<DiscussionAttachment> {

    private long id;
    private boolean locked;
    private boolean hidden;
    private boolean locked_for_user;
    private boolean hidden_for_user;
    private int size;
    private String lock_at;
    private String unlock_at;
    private String updated_at;
    private String created_at;
    private String display_name;
    private String filename;
    private String url;

    @SerializedName("content-type")
    private String content_type;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
    public boolean isHidden() {
        return hidden;
    }
    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public boolean isLockedForUser() {
        return locked_for_user;
    }
    public void setLockedForUser(boolean lockedForUser) {
        this.locked_for_user = lockedForUser;
    }
    public boolean isHiddenForUser() {
        return hidden_for_user;
    }
    public void setHiddenForUser(boolean hiddenForUser) {
        this.hidden_for_user = hiddenForUser;
    }
    public int getFileSize() {
        return size;
    }
    public void setFileSize(int fileSize) {
        this.size = fileSize;
    }
    public Date getLockAt() {
        return APIHelpers.stringToDate(lock_at);
    }
    public void setLockAt(Date lockAt) {
        this.lock_at = APIHelpers.dateToString(lockAt);
    }
    public Date getUnlockAt() {
        return APIHelpers.stringToDate(unlock_at);
    }
    public void setUnlockAt(Date unlockAt) {
        this.unlock_at = APIHelpers.dateToString(unlockAt);
    }
    public Date getUpdatedAt() {
        return APIHelpers.stringToDate(updated_at);
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updated_at = APIHelpers.dateToString(updatedAt);
    }
    public Date getCreatedAt() {
        return APIHelpers.stringToDate(created_at);
    }
    public void setCreatedAt(Date createdAt) {
        this.created_at = APIHelpers.dateToString(createdAt);
    }
    public String getDisplayName() {
        return display_name;
    }
    public void setDisplayName(String displayName) {
        this.display_name = displayName;
    }
    public String getFileName() {
        return filename;
    }
    public void setFileName(String fileName) {
        this.filename = fileName;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getMimeType() {
        return content_type;
    }
    public void setMimeType(String mimeType) {
        this.content_type = mimeType;
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
        return filename;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public DiscussionAttachment() {}

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public boolean shouldShowToUser() {
        if (hidden || hidden_for_user) {
            return false;
        } else if (locked || locked_for_user) {
            Date unlockAt = APIHelpers.stringToDate(unlock_at);

            if (unlock_at == null) {
                return false;
            } else {
                return new Date().after(unlockAt);
            }
        } else {
            return true;
        }
    }
}
