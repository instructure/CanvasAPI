package com.instructure.canvasapi.model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DiscussionTopic implements Serializable {

    private static final long serialVersionUID = 1L;

	//The user can't see it unless they post a high level reply (requireinitialpost).
	private boolean forbidden = false;

	//List of all the ids of the unread discussion entries.
    private Long[] unread_entries = new Long[0];
	
	//List of the participants.
    private DiscussionParticipant[] participants;
    private HashMap<Long, DiscussionParticipant> participantsMap;
    private HashMap<Long, Boolean> unread_entriesMap;

    //List of all the discussion entries (views)
    private DiscussionEntry[]view;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public boolean isForbidden() {
		return forbidden;
	}
	public void setForbidden(boolean forbidden) {
		this.forbidden = forbidden;
	}

    //This should only have to get built once.
    //    //MUCH faster for lookups.
    //So instead of n linear operations, we have 1 linear operations and (n-1) constant ones.
    public HashMap<Long,Boolean> getUnread_entriesMap(){
        if(unread_entriesMap == null){
            unread_entriesMap = new HashMap<Long, Boolean>();
            if(unread_entries != null){
                for(Long unreadEntry : unread_entries){
                    unread_entriesMap.put(unreadEntry,true);
                }
            }
        }
        return unread_entriesMap;
    }

    public Long[] getUnreadEntries() {
		return unread_entries;
	}

    //This should only have to get built once.
    //MUCH faster for lookups.
    //So instead of n linear operations, we have 1 linear operations and (n-1) constant ones.
    public HashMap<Long,DiscussionParticipant> getParticipantsMap(){
        if(participantsMap == null){
            participantsMap = new HashMap<Long, DiscussionParticipant>();
            if(participants != null){
                for(DiscussionParticipant discussionParticipant : participants){
                    participantsMap.put(discussionParticipant.getId(), discussionParticipant);
                }
            }
        }
        return participantsMap;
    }

    public void setUnreadEntries(Long[] unread_entries) {
        this.unread_entries = unread_entries;
    }
    public DiscussionParticipant[] getParticipants() {
        return participants;
    }
    public void setParticipants(DiscussionParticipant[] participants) {
        this.participants = participants;
    }
	public DiscussionEntry[] getViews() {
		return view;
	}

    public void setViews(DiscussionEntry[] views) {
		this.view = views;
	}

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public DiscussionTopic() {}


    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public static String getDiscussionURL(String api_protocol,String domain, long courseId, long topicId) {
        //https://mobiledev.instructure.com/api/v1/courses/24219/discussion_topics/1129998/
        return api_protocol + "://" + domain + "/courses/"+courseId+"/discussion_topics/"+topicId;
    }
}
