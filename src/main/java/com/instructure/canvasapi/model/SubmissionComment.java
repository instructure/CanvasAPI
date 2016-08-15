package com.instructure.canvasapi.model;

import android.content.Context;
import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.DateHelpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class SubmissionComment extends CanvasComparable<SubmissionComment> implements Serializable{
    private static final long serialVersionUID = 1L;

    private long author_id;
    private String author_name;
    private String comment;
    private String created_at;
    private MediaComment media_comment;
    private List<Attachment> attachments = new ArrayList<Attachment>();
    private Author author;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public long getAuthorID() {
		return author_id;
	}
	public String getAuthorName() {
		return author_name;
	}
	public String getComment() {
		return comment;
	}
	public String getCreatedAt() {
        if(created_at == null) {
            return null;
        }
		return created_at;
	}

    public Date getCreatedAtDate(){
        if(created_at == null) {
            return null;
        }
        return APIHelpers.stringToDate(created_at);
    }

	public MediaComment getMedia_comment() {
		return media_comment;
	}
    public List<Attachment> getAttachments(){
        if(attachments == null) {
            return new ArrayList<Attachment>();
        }
        return attachments;
    }
	public void setAuthorID(long authorID) {
		this.author_id = authorID;
	}
	public void setAuthorName(String authorName) {
		this.author_name = authorName;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setCreatedAt(String createdAt) {
		this.created_at = createdAt;
	}
	public void setMedia_comment(MediaComment media_comment) {
		this.media_comment = media_comment;
	}
	public Author getAuthor() {
	    return author;
	}
	public void setAuthor(Author author) {
	    this.author = author;
	}

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return getCreatedAtDate();
    }

    @Override
    public String getComparisonString() {
        return getAuthorName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public SubmissionComment() {}


    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public static String getFormattedDate(Context context, String date) {
        return DateHelpers.getDateTimeString(context, APIHelpers.stringToDate(date));
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.author_id);
        dest.writeString(this.author_name);
        dest.writeString(this.comment);
        dest.writeString(this.created_at);
        dest.writeParcelable(this.media_comment, flags);
        dest.writeList(this.attachments);
        dest.writeParcelable(this.author, flags);
    }

    private SubmissionComment(Parcel in) {
        this.author_id = in.readLong();
        this.author_name = in.readString();
        this.comment = in.readString();
        this.created_at = in.readString();
        this.media_comment = in.readParcelable(MediaComment.class.getClassLoader());
        in.readList(this.attachments, Attachment.class.getClassLoader());
        this.author = in.readParcelable(Author.class.getClassLoader());
    }

    public static Creator<SubmissionComment> CREATOR = new Creator<SubmissionComment>() {
        public SubmissionComment createFromParcel(Parcel source) {
            return new SubmissionComment(source);
        }

        public SubmissionComment[] newArray(int size) {
            return new SubmissionComment[size];
        }
    };
}
