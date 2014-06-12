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

public class Conversation extends CanvasModel<Conversation> {

    public enum WorkflowState {READ, UNREAD, ARCHIVED, UNKNOWN}

    private long id;                    // The unique id for the conversation.
    private String workflow_state;      // The workflowState of the conversation (unread, read, archived)
    private String last_message;        // <100 character preview of the last message.
    private String last_message_at;   // Date of the last message sent.
    private int message_count;          // Number of messages in the conversation.
    private boolean subscribed;         // Whether or not the user is subscribed to the current message.
    private boolean starred;            // Whether or not the message is starred.

    private String[] properties;

    private String avatar_url;          // The avatar to display. Knows if group, user, etc.
    private boolean visible;            // Whether this conversation is visible in the current context. Not 100% what that means.

    // The IDs of all people in the conversation. EXCLUDING the current user unless it's a monologue.
    private Long[] audience;
    //TODO: Audience contexts.

    // The name and IDs of all participants in the conversation.
    private BasicUser[] participants;

    // Messages attached to the conversation.
    private List<Message> messages;

    // helper variables
    private Date lastMessageDate;
	private boolean deleted = false; 	// Used to set whether or not we've determined it to be deleted with a failed retrofit call.
    private String deletedString = "";	// The string to show if something is deleted.

    ///////////////////////////////////////////////////////////////////////////
    // Constructor
    ///////////////////////////////////////////////////////////////////////////
    public Conversation(boolean deleted, String deletedStringtoShow){
        this.deleted = deleted;
        this.deletedString = deletedStringtoShow;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }
    public WorkflowState getWorkflowState() {
        if ("unread".equalsIgnoreCase(workflow_state)) {
            return WorkflowState.UNREAD;
        } else if ("archived".equalsIgnoreCase(workflow_state)) {
            return WorkflowState.ARCHIVED;
        } else if ("read".equalsIgnoreCase(workflow_state)) {
            return  WorkflowState.READ;
        } else {
            return WorkflowState.UNKNOWN;
        }
    }
    public void setId(long id){
        this.id = id;
    }
    public void setWorkflowState(WorkflowState state) {
        if(state == WorkflowState.UNREAD){
            workflow_state = "unread";
        } else if (state == WorkflowState.ARCHIVED){
            workflow_state = "archived";
        } else if (state == WorkflowState.READ){
            workflow_state = "read";
        } else{
            workflow_state = "";
        }
    }
    public void setLastMessage(String message){
        this.last_message = message;
    }
    public String getLastMessagePreview() {
        if(deleted){
            return deletedString;
        }
        return last_message;
    }
    public Date getLastMessageSent() {
        if (lastMessageDate == null) {
          lastMessageDate = APIHelpers.stringToDate(last_message_at);
        }
        return lastMessageDate;
    }
    public void setLastMessageSent(Date date) {
        lastMessageDate = date;
    }
    public int getMessageCount() {
        return message_count;
    }
    public String getLastMessageAt(){return last_message_at;}
    public boolean isSubscribed() {
        return subscribed;
    }
    public boolean isStarred() {
        return starred;
    }
    public boolean isLastAuthor() {
        for(int i = 0; i < properties.length; i++)
        {
            if(properties[i].equals("last_author")){
                return true;
            }
        }
        return false;
    }
    public boolean hasAttachments() {
        for(int i = 0; i < properties.length; i++)
        {
            if(properties[i].equals("attachments")){
                return true;
            }
        }
        return false;
    }
    public boolean hasMedia() {
        for(int i = 0; i < properties.length; i++)
        {
            if(properties[i].equals("media_objects")){
                return true;
            }
        }
        return false;
    }
    public Long[] getAudienceIDs() {
        return audience;
    }
    public BasicUser[] getAllParticipants() {
        return participants;
    }
    public String getAvatarURL() {
        return avatar_url;
    }
    public boolean isVisible() {
        return visible;
    }
    public boolean isMonologue (long myUserID) {
        return determineMonologue(myUserID);
    }
    public List<Message> getMessages() {
        return messages;
    }
    public String getMessageTitle(long myUserID, String monologue) {
        return determineMessageTitle(myUserID,monologue);
    }
    public boolean isDeleted(){return deleted;}

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    // We want opposite of natural sorting order of date since we want the newest one to come first
    @Override
    public Date getComparisonDate() {
        return getLastMessageSent();
    }

    @Override
    public String getComparisonString() {
        return getLastMessagePreview();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private boolean determineMonologue(long userID) {
        if(audience == null){
            return false;
        } else if (audience.length == 0){
            return true;
        }

        for(int i = 0; i < audience.length; i++){
            if(audience[i] == userID){
                return true;
            }
        }
        return false;
    }

    private String determineMessageTitle(long myUserID, String monologueDefault) {

        if(deleted){
            return deletedString;
        }
        else if (isMonologue(myUserID)) {
            return monologueDefault;
        }

        ArrayList<BasicUser> normalized = new ArrayList<BasicUser>();

        //Normalize the message!
        for (int i = 0; i < getAllParticipants().length; i++) {
            if (getAllParticipants()[i].getId() == myUserID) {
                continue;
            } else {
                normalized.add(getAllParticipants()[i]);
            }
        }

        if (normalized.size() > 2) {
            return normalized.get(0).getUsername() + String.format(" and %d more", normalized.size() - 1);
        } else {
            String participants = "";
            for (int i = 0; i < normalized.size(); i++) {
                if (!participants.equals("")) {
                    participants += ", ";
                }

                participants += normalized.get(i).getUsername();
            }
            return  participants;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.workflow_state);
        dest.writeString(this.last_message);
        dest.writeString(this.last_message_at);
        dest.writeInt(this.message_count);
        dest.writeByte(subscribed ? (byte) 1 : (byte) 0);
        dest.writeByte(starred ? (byte) 1 : (byte) 0);
        dest.writeStringArray(this.properties);
        dest.writeString(this.avatar_url);
        dest.writeByte(visible ? (byte) 1 : (byte) 0);
        dest.writeArray(this.audience);
        dest.writeParcelableArray(this.participants, flags);
        dest.writeList(this.messages);
        dest.writeLong(lastMessageDate != null ? lastMessageDate.getTime() : -1);
        dest.writeByte(deleted ? (byte) 1 : (byte) 0);
        dest.writeString(this.deletedString);
    }

    private Conversation(Parcel in) {
        this.id = in.readLong();
        this.workflow_state = in.readString();
        this.last_message = in.readString();
        this.last_message_at = in.readString();
        this.message_count = in.readInt();
        this.subscribed = in.readByte() != 0;
        this.starred = in.readByte() != 0;
        this.properties = in.createStringArray();
        this.avatar_url = in.readString();
        this.visible = in.readByte() != 0;
        this.audience = (Long[]) in.readArray(Long.class.getClassLoader());
        this.participants = (BasicUser[])in.readParcelableArray(BasicUser.class.getClassLoader());
        this.messages = new ArrayList<Message>();
        in.readList(this.messages, Message.class.getClassLoader());
        long tmpLastMessageDate = in.readLong();
        this.lastMessageDate = tmpLastMessageDate == -1 ? null : new Date(tmpLastMessageDate);
        this.deleted = in.readByte() != 0;
        this.deletedString = in.readString();
    }

    public static Creator<Conversation> CREATOR = new Creator<Conversation>() {
        public Conversation createFromParcel(Parcel source) {
            return new Conversation(source);
        }

        public Conversation[] newArray(int size) {
            return new Conversation[size];
        }
    };
}
