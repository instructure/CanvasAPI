package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.google.gson.annotations.SerializedName;
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

    private long             id;
    @SerializedName("author_id")
    private long             authorId;
    private boolean          generated;
    private String           body;
    @SerializedName("created_at")
    private String           createdAt;
    private Submission       submission;
    @SerializedName("media_comment")
    private MediaComment     mediaComment;
    private List<Attachment> attachments          = new ArrayList<>();
    @SerializedName("forwarded_messages")
    private List<Message>    forwardedMessages    = new ArrayList<>();
    @SerializedName("participating_user_ids")
    private List<Long>       participatingUserIds = new ArrayList<>();

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
    public Date getCreationDate() {
        return APIHelpers.stringToDate(createdAt);
    }
    public void setCreationDate(String createdAt) {
        this.createdAt = createdAt;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public long getAuthorID() {
        return authorId;
    }
    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }
    public boolean isGenerated() {
        return generated;
    }
    public MediaComment getMediaComment() {
        return mediaComment;
    }
    public void setMediaComment(MediaComment mediaComment) {
        this.mediaComment = mediaComment;
    }
    public List<Attachment> getAttachments() {
        return attachments;
    }
    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    public List<Message> getForwardedMessages() {
        return forwardedMessages;
    }
    public void setForwardedMessages(List<Message> forwardedMessages) {
        this.forwardedMessages = forwardedMessages;
    }
    public Submission getSubmission() {
        return submission;
    }
    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
    public Date getMessageDate() {
        return APIHelpers.stringToDate(createdAt);
    }
    public List<Long> getParticipatingUserIds() {
        return participatingUserIds;
    }

    public void setParticipatingUserIds(List<Long> participating_user_ids) {
        this.participatingUserIds = participating_user_ids;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public Date getComparisonDate() {
        return APIHelpers.stringToDate(createdAt);
    }

    @Override
    public String getComparisonString() {
        return body;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.createdAt);
        dest.writeString(this.body);
        dest.writeLong(this.authorId);
        dest.writeByte(generated ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.mediaComment, flags);
        dest.writeParcelable(this.submission, flags);
        dest.writeList(this.attachments);
        dest.writeList(this.forwardedMessages);
        dest.writeList(this.participatingUserIds);
    }

    public Message() {}

    private Message(Parcel in) {
        this.id           = in.readLong();
        this.createdAt    = in.readString();
        this.body         = in.readString();
        this.authorId     = in.readLong();
        this.generated    = in.readByte() != 0;
        this.mediaComment = in.readParcelable(MediaComment.class.getClassLoader());
        this.submission   = in.readParcelable(Submission.class.getClassLoader());
        in.readList(this.attachments, Attachment.class.getClassLoader());
        in.readList(this.forwardedMessages, Message.class.getClassLoader());
        in.readList(this.participatingUserIds, Long.class.getClassLoader());
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
