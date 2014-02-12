package com.instructure.canvasapi.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Hoa Hoang
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Avatar extends CanvasComparable<Avatar> implements Serializable {

    public static final long serialVersionUID = 1L;

	private String type;
	private String url;
	private String token;
	private String display_name;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getDisplayName() {
		return display_name;
	}
	public void setDisplayName(String displayName) {
		this.display_name = displayName;
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
}
