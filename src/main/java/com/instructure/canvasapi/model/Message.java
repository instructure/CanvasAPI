package com.instructure.canvasapi.model;

import android.os.Parcel;
import com.instructure.canvasapi.utilities.APIHelpers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<Attachment> attachments = new ArrayList<>();
    private MediaComment media_comment;
    private Submission submission;
    private List<Message> forwarded_messages = new ArrayList<>();

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
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public List<Message> getForwardedMessages() {
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
        return APIHelpers.stringToDate(created_at);
    }

    @Override
    public String getComparisonString() {
        return body;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.created_at);
        dest.writeString(this.body);
        dest.writeLong(this.author_id);
        dest.writeByte(generated ? (byte) 1 : (byte) 0);
        dest.writeList(this.attachments);
        dest.writeParcelable(this.media_comment, flags);
        dest.writeParcelable(this.submission, flags);
        dest.writeList(this.forwarded_messages);
    }

    public Message() {}

    private Message(Parcel in) {
        this.id = in.readLong();
        this.created_at = in.readString();
        this.body = in.readString();
        this.author_id = in.readLong();
        this.generated = in.readByte() != 0;
        in.readList(this.attachments, Attachment.class.getClassLoader());
        this.media_comment = in.readParcelable(MediaComment.class.getClassLoader());
        this.submission = in.readParcelable(Submission.class.getClassLoader());
        in.readList(this.forwarded_messages, Message.class.getClassLoader());
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
