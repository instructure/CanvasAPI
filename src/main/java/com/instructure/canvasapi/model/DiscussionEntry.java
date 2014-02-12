package com.instructure.canvasapi.model;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.*;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DiscussionEntry extends CanvasModel<DiscussionEntry> {

    private long id;                      //Entry id.
    private boolean unread = false;
    private String updated_at;
    private String created_at;
    private DiscussionEntry parent;         //Parent of the entry;
    private DiscussionParticipant author;
    private String description;             //HTML formatted string used for an edge case. Converting header to entry
    private long user_id;                   //Id of the user that posted it.
    private long parent_id = -1;            //Parent id. -1 if there isn't one.
    private String message;                 //HTML message.
    private boolean deleted;                //Whether the quthor deleted the message. If true, the message will be null.
    private int totalChildren = 0;
    private int unreadChildren = 0;
    private DiscussionEntry[] replies = new DiscussionEntry[0];  //Nested messages.
    private DiscussionAttachment[] attachments = new DiscussionAttachment[0];

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

    public ArrayList<DiscussionAttachment> getAttachments() {
        return new ArrayList<DiscussionAttachment>(Arrays.asList(attachments));
    }

    public void setAttachments(ArrayList<DiscussionAttachment> attachments) {
        this.attachments = (DiscussionAttachment[]) attachments.toArray();
    }

    public void setAttachments(DiscussionAttachment[] attachments) {
        this.attachments = attachments;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public void setTotalChildren(int total) {
        totalChildren = total;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setUnreadChildren(int unread) {
        unreadChildren = unread;
    }

    public int getUnreadChildren() {
        return unreadChildren;
    }

    public void setAuthor(DiscussionParticipant discussionParticipant) {
        author = discussionParticipant;
    }

    public DiscussionParticipant getAuthor() {
        return author;
    }

    public Date getCreatedAt() {
        return APIHelpers.stringToDate(created_at);
    }

    public void setCreatedAt(Date date) {
        created_at = APIHelpers.dateToString(date);
    }

    public Date getLastUpdated() {
        return APIHelpers.stringToDate(updated_at);
    }

    public void setLastUpdated(Date date) {
        updated_at = APIHelpers.dateToString(date);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String d) {
        description = d;
    }

    public DiscussionEntry getParent() {
        return parent;
    }

    public void setParent(DiscussionEntry parent) {
        this.parent = parent;
    }

    public long getUserId() {
        return user_id;
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public long getParentId() {
        return parent_id;
    }

    public void setParentId(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getMessage(String localizedDeletedString) {
        if (message == null || message.equals("null")) {
            if (deleted)
                return localizedDeletedString;
            else
                return "";
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void addReply (DiscussionEntry entry){
        ArrayList<DiscussionEntry> entries = new ArrayList<DiscussionEntry>(Arrays.asList(replies));
        entries.add(entry);
        setReplies(entries);
    }


    public DiscussionEntry[] getReplies() {
        return replies;
    }

    public void setReplies(List<DiscussionEntry> replies) {
        if (replies == null) {
            this.replies = null;
        }

        DiscussionEntry[] entries = new DiscussionEntry[replies.size()];
        this.replies = replies.toArray(entries);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return getLastUpdated();
    }

    @Override
    public String getComparisonString() {
        return message;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public DiscussionEntry() {
    }

    public void init(DiscussionTopic topic, DiscussionEntry parent) {
        this.parent = parent;


        HashMap<Long, DiscussionParticipant> participantHashMap = topic.getParticipantsMap();
        DiscussionParticipant discussionParticipant = participantHashMap.get(this.getUserId());
        if(discussionParticipant != null){
            author = discussionParticipant;
        }

        //Get whether or not the topic is unread;
        unread = topic.getUnread_entriesMap().containsKey(this.getId());

        for(DiscussionEntry reply : replies){
            reply.init(topic,this);

            //Handle total and unread children.
            unreadChildren += reply.getUnreadChildren();
            if (reply.isUnread())
                unreadChildren++;

            totalChildren++;
            totalChildren += reply.getTotalChildren();
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public int getDepth() {
        int depth = 0;
        DiscussionEntry temp = this;

        while (temp.getParent() != null) {
            depth++;
            temp = temp.getParent();
        }

        return depth;
    }
}
