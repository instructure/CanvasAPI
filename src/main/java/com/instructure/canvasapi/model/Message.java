package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Message extends CanvasModel<Message> {

    private long id;
    private String created_at;
    private String body;
    private long author_id;
    private boolean generated;
    private Attachment[] attachments;
    private MediaComment media_comment;
    private Submission submission;
    private Message[] forwarded_messages;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }
    public Date getCreationDate() {
        return APIHelpers.stringToDate(created_at);
    }
    public String getBody() {
        return body;
    }
    public long getAuthorID() {
        return author_id;
    }
    public boolean isGenerated() {
        return generated;
    }
    public MediaComment getMediaComment() {
        return media_comment;
    }
    public Attachment[] getAttachments() {
        return attachments;
    }
    public Message[] getForwardedMessages() {
        return forwarded_messages;
    }
    public Submission getSubmission() {
        return submission;
    }

    public Date getMessageDate() {
        return APIHelpers.stringToDate(created_at);
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
        return body;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.body);
        dest.writeLong(this.author_id);
        dest.writeByte(generated ? (byte) 1 : (byte) 0);
        dest.writeSerializable(this.attachments);
        dest.writeParcelable(this.media_comment, flags);
        dest.writeParcelable(this.submission, flags);
        dest.writeSerializable(this.forwarded_messages);
    }

    public Message() {
    }

    private Message(Parcel in) {
        this.id = in.readLong();
        this.created_at = in.readString();
        this.body = in.readString();
        this.author_id = in.readLong();
        this.generated = in.readByte() != 0;
        this.attachments =(Attachment[]) in.readSerializable();
        this.media_comment = in.readParcelable(MediaComment.class.getClassLoader());
        this.submission = in.readParcelable(Submission.class.getClassLoader());
        this.forwarded_messages =(Message[]) in.readSerializable();
    }

    public static Creator<Message> CREATOR = new Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
