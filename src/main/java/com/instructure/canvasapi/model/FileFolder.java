package com.instructure.canvasapi.model;

import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class FileFolder extends CanvasModel<FileFolder> {

	/**
	 * {
		  "size":4,
		  "content-type":"text/plain",
		  "url":"http://www.example.com/files/569/download?download_frd=1\u0026verifier=c6HdZmxOZa0Fiin2cbvZeI8I5ry7yqD7RChQzb6P",
		  "id":569,
		  "display_name":"file.txt",
		  "created_at':"2012-07-06T14:58:50Z",
		  "updated_at':"2012-07-06T14:58:50Z",
		  "unlock_at':null,
		  "locked':false,
		  "hidden':false,
		  "lock_at':null,
		  "locked_for_user":false,
		  "hidden_for_user":false
		}
	 */
	//file
	private long size;
    @SerializedName("content-type")
	private String content_type;
	private String url;
	private String display_name;
	
	//folder
	private String context_type;
	private long context_id;
	private int files_count;
	private int position;
	private int folders_count;
	private String name;
	private long parent_folder_id;
	private String folders_url;
	private String files_url;
	private String full_name;
	
	//common to both
	private long id;
	private String created_at;
	private String updated_at;
	private String unlock_at;
	private boolean locked;
	private boolean hidden;
	private String lock_at;
	private boolean locked_for_user;
	private boolean hidden_for_user;
    private LockInfo lock_info;
    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
	
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getContentType() {
		return content_type;
	}
	public void setContentType(String content_type) {
		this.content_type = content_type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    @Override
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDisplayName() {
		return display_name;
	}
	public void setDisplayName(String display_name) {
		this.display_name = display_name;
	}
	public String getContextType() {
		return context_type;
	}
	public void setContextType(String context_type) {
		this.context_type = context_type;
	}
	public long getContextId() {
		return context_id;
	}
	public void setContextId(long context_id) {
		this.context_id = context_id;
	}
	public int getFilesCount() {
		return files_count;
	}
	public void setFilesCount(int files_count) {
		this.files_count = files_count;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getFolders_count() {
		return folders_count;
	}
	public void setFoldersCount(int folders_count) {
		this.folders_count = folders_count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getParentFolderId() {
		return parent_folder_id;
	}
	public void setParentFolderId(long parent_folder_id) {
		this.parent_folder_id = parent_folder_id;
	}
	public String getFoldersUrl() {
		return folders_url;
	}
	public void setFoldersUrl(String folders_url) {
		this.folders_url = folders_url;
	}
	public String getFilesUrl() {
		return files_url;
	}
	public void setFilesUrl(String files_url) {
		this.files_url = files_url;
	}
	public String getFullName() {
		return full_name;
	}
	public void setFullName(String full_name) {
		this.full_name = full_name;
	}
	
	public Date getCreatedAt() {
		return APIHelpers.stringToDate(created_at);
	}
	public void setCreatedAt(Date created_at) {
		this.created_at = APIHelpers.dateToString(created_at);
	}
	public Date getUpdatedAt() {
		return APIHelpers.stringToDate(updated_at);
	}
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = APIHelpers.dateToString(updated_at);
	}
	public Date getUnlockAt() {
        return APIHelpers.stringToDate(unlock_at);
	}
	public void setUnlockAt(Date unlock_at) {
		this.unlock_at = APIHelpers.dateToString(unlock_at);
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
//	public Date getLock_at() {
//		return lock_at;
//	}
//	public void setLock_at(Date lock_at) {
//		this.lock_at = lock_at;
//	}
	public boolean isLockedForUser() {
		return locked_for_user;
	}
	public void setLockedForUser(boolean locked_for_user) {
		this.locked_for_user = locked_for_user;
	}
	public boolean isHiddenForUser() {
		return hidden_for_user;
	}
	public void setHiddenForUser(boolean hidden_for_user) {
		this.hidden_for_user = hidden_for_user;
	}

    //During parsing, GSON will try. Which means sometimes we get 'empty' objects
    //They're non-null, but don't have any information.
    public LockInfo getLockInfo() {
        if(lock_info == null || lock_info.isEmpty()){
            return null;
        }

        return lock_info;
    }

    public void setLockInfo(LockInfo lockInfo) {
        this.lock_info = lockInfo;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    // we override compareTo instead of using these
    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

	public FileFolder() {}

    /**
     * Example JSON response
     {
     "size":4,
     "content-type":"text/plain",
     "url":"http://www.example.com/files/569/download?download_frd=1\u0026verifier=c6HdZmxOZa0Fiin2cbvZeI8I5ry7yqD7RChQzb6P",
     "id":569,
     "display_name":"file.txt",
     "created_at':"2012-07-06T14:58:50Z",
     "updated_at':"2012-07-06T14:58:50Z",
     "unlock_at':null,
     "locked':false,
     "hidden':false,
     "lock_at':null,
     "locked_for_user":false,
     "hidden_for_user":false
     }
     */

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(FileFolder other) {
        // folders go before files

        // this is a folder and other is a file
        if (getFullName() != null && other.getFullName() == null) {
            return -1;
        } // this is a file and other is a folder
        else if (getFullName() == null && other.getFullName() != null) {
            return 1;
        }
        // both are folders
        if (getFullName() != null && other.getFullName() != null) {
            return getFullName().compareTo(other.getFullName());
        }
        // both are files
        return getDisplayName().compareTo(other.getDisplayName());
    }
}
