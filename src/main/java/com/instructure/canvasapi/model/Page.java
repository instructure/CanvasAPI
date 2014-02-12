package com.instructure.canvasapi.model;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Page extends CanvasComparable<Page> implements Serializable {

    private static final long serialVersionUID = 1L;

    /* Example JSON response
 *
 * {
      // the unique locator for the page
      url: "my-page-title",

      // the title of the page
      title: "My Page Title",

      // the creation date for the page
      created_at: "2012-08-06T16:46:33-06:00",

      // the date the page was last updated
      updated_at: "2012-08-08T14:25:20-06:00",

      // whether this page is hidden from students
      // (note: students will never see this true; pages hidden from them will be omitted from results)
      hide_from_students: false,

      // the page content, in HTML
      // (present when requesting a single page; omitted when listing pages)
      body: "<p>Page Content</p>"
    }
 */

	private String url;
	private String title;
	private String created_at;
	private String updated_at;
	private boolean hide_from_students;
    private String status;
	private String body;
    private LockInfo lock_info;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreate_at() {
		return APIHelpers.stringToDate(created_at);
	}
	public void setCreate_at(Date create_at) {
		this.created_at = APIHelpers.dateToString(create_at);
	}
	public Date getUpdated_at() {
        return APIHelpers.stringToDate(updated_at);
	}
	public void setUpdated_at(Date updated_at) {
        this.created_at = APIHelpers.dateToString(updated_at);
	}
	public boolean isHide_from_students() {
		return hide_from_students || (status != null && status.equalsIgnoreCase("unauthorized"));
	}
	public void setHide_from_students(boolean hide_from_students) {
		this.hide_from_students = hide_from_students;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}


    //During parsing, GSON will try. Which means sometimes we get 'empty' objects
    //They're non-null, but don't have any information.
    public LockInfo getLockInfo() {

        //Check for null or empty lock info.
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

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getTitle();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Page() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        if (url != null ? !url.equals(page.url) : page.url != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
