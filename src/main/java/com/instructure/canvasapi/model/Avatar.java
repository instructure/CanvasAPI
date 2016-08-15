package com.instructure.canvasapi.model;

import android.os.Parcel;

import java.util.Date;

/**
 * @author Hoa Hoang
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Avatar extends CanvasComparable<Avatar> {

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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeString(this.token);
        dest.writeString(this.display_name);
    }

    public Avatar() {
    }

    private Avatar(Parcel in) {
        this.type = in.readString();
        this.url = in.readString();
        this.token = in.readString();
        this.display_name = in.readString();
    }

    public static Creator<Avatar> CREATOR = new Creator<Avatar>() {
        public Avatar createFromParcel(Parcel source) {
            return new Avatar(source);
        }

        public Avatar[] newArray(int size) {
            return new Avatar[size];
        }
    };
}
