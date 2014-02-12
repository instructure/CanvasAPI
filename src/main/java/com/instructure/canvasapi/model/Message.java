package com.instructure.canvasapi.model;

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

}