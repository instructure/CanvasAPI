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

public class DiscussionTopicHeader extends CanvasModel<DiscussionTopicHeader>{

    public enum ReadState { READ, UNREAD }
    public enum DiscussionType { UNKNOWN, SIDE_COMMENT, THREADED }

    private long id;                //Discussion Topic Id;
    private String discussion_type; //Type of discussion (side_comment or threaded).
    private String title;           //Discussion title
    private String message;         //HTML content
    private String html_url;         //URL to the topic on canvas.

    //Only one of the following two will be filled out. the other will be null.
    //If posted_at isn't null, it represents when the discussion WAS posted.
    //If delayed_post_at isn't null, it represents when the discussion WILL be posted.
    private String posted_at;
    private String delayed_post_at;

    private String last_reply_at;              //Last response to the thread.
    private boolean require_initial_post;   //Whether or not users are required to post before they can respond to comments.
    private int discussion_subentry_count;  // The count of entries in the topic.
    private String read_state;                  //Whether or not the topic has been read yet.
    private int unread_count;               //Number of unread messages.
    private long assignment_id;             // The unique identifier of the assignment if the topic is for grading, otherwise null.
    private boolean locked;                 // whether or not this is locked for students to see.
    private boolean pinned;                 // whether or not the discussion has been "pinned" by an instructor
    private DiscussionParticipant author;  //The user that started the thread.
    private String podcast_url;             // If the topic is a podcast topic this is the feed url for the current user.

    // If the topic is for grading and a group assignment this will
    // point to the original topic in the course.
    //String maybe?
    private long root_topic_id;

    // A list of topic_ids for the group discussions the user is a part of.
    private List<Long> topic_children;

    //List of file attachments
    private List<DiscussionAttachment> attachments;

    public boolean unauthorized;
    private DiscussionTopicPermission permission;
    private Assignment assignment;
    private LockInfo lock_info;

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
	public DiscussionType getType() {
        if("side_comment".equals(this.discussion_type)){
            return DiscussionType.SIDE_COMMENT;
        } else if ("threaded".equals(this.discussion_type)){
            return DiscussionType.THREADED;
        }
        return DiscussionType.UNKNOWN;
	}
	public void setType(DiscussionType type) {
        if(type == DiscussionType.SIDE_COMMENT) {
            this.discussion_type = "side_comment";
        } else if (type == DiscussionType.THREADED) {
            this.discussion_type = "threaded";
        } else {
            this.discussion_type = "";
        }
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		if(message == null || message.equals("null")) {
			return "";
		} else {
			return message;
        }
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHtmlUrl() {
		return html_url;
	}
	public void setHtmlUrl(String html_url) {
		this.html_url = html_url;
	}
	public Date getPostedAt() {
		return APIHelpers.stringToDate(posted_at);
	}
	public void setPostedAt(Date posted_at) {
		this.posted_at = APIHelpers.dateToString(posted_at);
	}
	public Date getDelayedPostAt() {
		return APIHelpers.stringToDate(delayed_post_at);
	}
	public void setDelayedPostAt(Date delayed_post_at) {
		this.delayed_post_at = APIHelpers.dateToString(delayed_post_at);
	}
	public Date getLastReply() {
		return APIHelpers.stringToDate(last_reply_at);
	}
	public void setLastReply(Date last_reply) {
		this.last_reply_at = APIHelpers.dateToString(last_reply);
	}
	public boolean requiresInitialPost() {
		return require_initial_post;
	}
	public void setRequireInitialPost(boolean require_initial_post) {
		this.require_initial_post = require_initial_post;
	}
	public int getDiscussionSubentryCount() {
		return discussion_subentry_count;
	}
	public void setDiscussionSubentryCount(int discussion_subentry_count) {
		this.discussion_subentry_count = discussion_subentry_count;
	}
	public ReadState getStatus() {
        if("read".equals(read_state)) {
            return ReadState.READ;
        } else if ("unread".equals(read_state)) {
            return ReadState.UNREAD;
        } else {
            return ReadState.UNREAD;
        }
	}
	public void setStatus(ReadState status) {
		if (status == ReadState.READ){
            this.read_state = "read";
        } else {
            this.read_state = "unread";
        }
	}
	public int getUnreadCount() {
		return unread_count;
	}
	public void setUnreadCount(int unread_count) {
		this.unread_count = unread_count;
	}
	public long getAssignmentId() {
		return assignment_id;
	}
	public void setAssignmentId(long assignment_id) {
		this.assignment_id = assignment_id;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
    public boolean isPinned() {
        return pinned;
    }
    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
	public DiscussionParticipant getCreator() {
		return author;
	}
	public void setCreator(DiscussionParticipant creator) {
		this.author = creator;
	}
	public long getRootTopicId() {
		return root_topic_id;
	}
	public void setRootTopicId(long root_topic_id) {
		this.root_topic_id = root_topic_id;
	}
	public List<Long> getTopicChildren() {
		return topic_children;
	}
	public void setTopicChildren(List<Long> topic_children) {
        if(topic_children != null){
            this.topic_children = topic_children;
        }
	}
	public String getPodcastUrl() {
		return podcast_url;
	}
	public void setPodcastUrl(String podcast_url) {
		this.podcast_url = podcast_url;
	}
	public List<DiscussionAttachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<DiscussionAttachment> attachments) {
        if(attachments != null){
            this.attachments = attachments;
        }
	}
	public DiscussionTopicPermission getPermission() {
		return permission;
	}
	public void setPermission(DiscussionTopicPermission permission) {
		this.permission = permission;
	}

    //During parsing, GSON will try. Which means sometimes we get 'empty' objects
    //They're non-null, but don't have any information.
    public LockInfo getLockInfo() {
        if (lock_info == null || lock_info.isEmpty()){
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
        return getPostedAt();
    }

    @Override
    public String getComparisonString() {
        return getTitle();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public DiscussionTopicHeader() {}
	
	public DiscussionTopicHeader(boolean unAuthorized) {
		this.unauthorized = unAuthorized;
	}

    public DiscussionTopicHeader(long id) {
        this.id = id;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public DiscussionEntry convertToDiscussionEntry(String localizedGradedDiscussion, String localizedPointsPossible) {
        DiscussionEntry discussionEntry = new DiscussionEntry();
        discussionEntry.setMessage(this.message);
        discussionEntry.setParent(null);
        discussionEntry.setParentId(-1);
        discussionEntry.setReplies(new ArrayList<DiscussionEntry>());

        String description = "";
        if(assignment != null) {
            description = localizedGradedDiscussion;
            if(assignment.getPointsPossible() > 0)
                description += "<br>"+Double.toString(assignment.getPointsPossible()) + " " + localizedPointsPossible;
        }
        discussionEntry.setDescription(description);

        discussionEntry.setMessage(this.getMessage());

        if(this.getLastReply() != null) {
            discussionEntry.setLastUpdated(this.getLastReply());
        } else if(this.getPostedAt() != null) {
            discussionEntry.setLastUpdated(this.getPostedAt());
        } else {
            discussionEntry.setLastUpdated(this.getDelayedPostAt());
        }

        discussionEntry.setAuthor(author);

        discussionEntry.setAttachments(this.getAttachments());

        discussionEntry.setUnread(this.getStatus() == ReadState.UNREAD);

        return discussionEntry;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.discussion_type);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeString(this.html_url);
        dest.writeString(this.posted_at);
        dest.writeString(this.delayed_post_at);
        dest.writeString(this.last_reply_at);
        dest.writeByte(require_initial_post ? (byte) 1 : (byte) 0);
        dest.writeInt(this.discussion_subentry_count);
        dest.writeString(this.read_state);
        dest.writeInt(this.unread_count);
        dest.writeLong(this.assignment_id);
        dest.writeByte(locked ? (byte) 1 : (byte) 0);
        dest.writeByte(pinned ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.author, flags);
        dest.writeString(this.podcast_url);
        dest.writeLong(this.root_topic_id);
        dest.writeList(this.topic_children);
        dest.writeList(this.attachments);
        dest.writeByte(unauthorized ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.permission, flags);
        dest.writeParcelable(this.assignment, flags);
        dest.writeParcelable(this.lock_info, flags);
    }

    private DiscussionTopicHeader(Parcel in) {

        this.id = in.readLong();
        this.discussion_type = in.readString();
        this.title = in.readString();
        this.message = in.readString();
        this.html_url = in.readString();
        this.posted_at = in.readString();
        this.delayed_post_at = in.readString();
        this.last_reply_at = in.readString();
        this.require_initial_post = in.readByte() != 0;
        this.discussion_subentry_count = in.readInt();
        this.read_state = in.readString();
        this.unread_count = in.readInt();
        this.assignment_id = in.readLong();
        this.locked = in.readByte() != 0;
        this.pinned = in.readByte() != 0;
        this.author = in.readParcelable(DiscussionParticipant.class.getClassLoader());
        this.podcast_url = in.readString();
        this.root_topic_id = in.readLong();

        this.topic_children = new ArrayList<Long>();
        in.readList(this.topic_children, Long.class.getClassLoader());

        this.attachments = new ArrayList<DiscussionAttachment>();
        in.readList(this.attachments, DiscussionAttachment.class.getClassLoader());

        this.unauthorized = in.readByte() != 0;
        this.permission =  in.readParcelable(DiscussionTopicPermission.class.getClassLoader());
        this.assignment = in.readParcelable(Assignment.class.getClassLoader());
        this.lock_info =  in.readParcelable(LockInfo.class.getClassLoader());
    }

    public static Creator<DiscussionTopicHeader> CREATOR = new Creator<DiscussionTopicHeader>() {
        public DiscussionTopicHeader createFromParcel(Parcel source) {
            return new DiscussionTopicHeader(source);
        }

        public DiscussionTopicHeader[] newArray(int size) {
            return new DiscussionTopicHeader[size];
        }
    };
}
