package com.instructure.canvasapi.model;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.R;
import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;
import java.util.Map;

/**
 * Created by Joshua Dutton on 10/22/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class StreamItem extends CanvasModel<StreamItem> {

    public enum Type { DISCUSSION_TOPIC, SUBMISSION, ANNOUNCEMENT, CONVERSATION, MESSAGE, CONFERENCE, COLLABORATION, COLLECTION_ITEM, UNKNOWN, NOT_SET;
        public static boolean isDiscussionTopic(StreamItem streamItem) {return streamItem.getType() == DISCUSSION_TOPIC;}
        public static boolean isSubmission(StreamItem streamItem) {return streamItem.getType() == SUBMISSION;}
        public static boolean isAnnouncement(StreamItem streamItem) {return streamItem.getType() == ANNOUNCEMENT;}
        public static boolean isConversation(StreamItem streamItem) {return streamItem.getType() == CONVERSATION;}
        public static boolean isMessage(StreamItem streamItem) {return streamItem.getType() == MESSAGE;}
        public static boolean isConference(StreamItem streamItem) {return streamItem.getType() == CONFERENCE;}
        public static boolean isCollaboration(StreamItem streamItem) {return streamItem.getType() == COLLABORATION;}
        public static boolean isCollectionItem(StreamItem streamItem) {return streamItem.getType() == COLLECTION_ITEM;}
        public static boolean isUnknown(StreamItem streamItem) {return streamItem.getType() == UNKNOWN;}
        public static boolean isNotSet(StreamItem streamItem) {return streamItem.getType() == NOT_SET;}
    };

    // general info returned by the API
    private String updated_at;
    private long id;
    private String title;
    private String message;
    private String type;
    private String context_type;
    private boolean read_state;
    private String url;
    private String html_url;
    private long course_id = -1;
    private long group_id = -1;
    private long assignment_id = -1;

    // message type, which is not a conversation, but stream notifications
    private long message_id;
    private String notification_category;

    // conversation
    private long conversation_id;
    @SerializedName("private")
    private boolean isPrivate;
    private int participant_count;

    // discussionTopic or announcement
    private long discussion_topic_id = -1;
    private long announcement_id;
    private int total_root_discussion_entries;
    private boolean require_initial_post;
    private boolean user_has_posted;
    private DiscussionEntry[] root_discussion_entries;

    // submission
    private int attempt;
    private String body;
    private String grade;
    private boolean grade_matches_current_submission;
    private String graded_at;
    private long grader_id;
    private double score = -1.0;
    private String submission_type;
    private String submitted_at;
    private String workflow_state;
    private boolean late;
    private String preview_url;
    private SubmissionComment[] submission_comments;
    private CanvasContext canvasContext;
    private Assignment assignment;
    private long user_id;
    private User user;

    // helper fields
    private Type enumType = Type.NOT_SET;
    private CanvasContext.Type canvasContextType = CanvasContext.Type.USER;
    private boolean hasSetContextType = false;
    private Date updatedAtDate;
    private Date gradedAtDate;
    private Date submittedAtDate;
    private Conversation conversation;
    private boolean isChecked;

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    // We want opposite of natural sorting order of date since we want the newest one to come first
    @Override
    public Date getComparisonDate() {
        return  getUpdatedAtDate();
    }

    @Override
    public String getComparisonString() {
        return title;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Getters
    ///////////////////////////////////////////////////////////////////////////

    public Date getUpdatedAtDate() {
        if (updatedAtDate == null) {
            updatedAtDate = APIHelpers.stringToDate(updated_at);
        }
        return updatedAtDate;
    }
    public long getId() {
        return id;
    }
    public String getTitle(Context context) {
        if (title == null && getType() == Type.CONVERSATION) {
            title = context.getString(R.string.Message);
        }
        return title;
    }
    public String getMessage(Context context) {
        if (message == null) {
            message = createMessage(context);
        }
        return message;
    }
    public Type getType() {
        if (enumType == Type.NOT_SET) {
            enumType = typeFromString(type);
        }
        return enumType;
    }
    public CanvasContext.Type getContextType() {
        if (!hasSetContextType) {
            if (context_type != null && (context_type.toLowerCase().equals("course") || course_id > 0)) {
                canvasContextType = CanvasContext.Type.COURSE;
            } else if (context_type != null && (context_type.toLowerCase().equals("group") || group_id > 0)){
                canvasContextType = CanvasContext.Type.GROUP;
            }
            hasSetContextType = true;
        }

        return canvasContextType;
    }
    public boolean isReadState() {
        return read_state;
    }
    public String getUrl() {
        return url;
    }
    public String getHtmlUrl() {
        return html_url;
    }
    public long getCourseId() {
        if (getContextType() == CanvasContext.Type.COURSE && course_id == -1) {
            course_id = parseCourseId();
        }
        return course_id;
    }

    public long getGroupId() {
        if (getContextType() == CanvasContext.Type.GROUP && group_id == -1) {
            group_id = parseGroupId();
        }
        return group_id;
    }
    public long getAssignmentId() {
        if (getContextType() == CanvasContext.Type.COURSE && assignment_id == -1) {
            assignment_id = parseAssignmentId();
        }
        return assignment_id;
    }
    public long getMessageId() {
        return message_id;
    }
    public String getNotificationCategory() {
        return notification_category;
    }
    public long getConversationId() {
        return conversation_id;
    }
    public boolean isPrivate() {
        return isPrivate;
    }
    public int getParticipantCount() {
        return participant_count;
    }
    public long getDiscussionTopicId() {
        if (discussion_topic_id == -1) {
            return announcement_id;
        }
        return discussion_topic_id;
    }
    public int getTotalRootDiscussionEntries() {
        return total_root_discussion_entries;
    }
    public boolean requiresInitialPost() {
        return require_initial_post;
    }
    public boolean userHasPosted() {
        return user_has_posted;
    }
    public DiscussionEntry[] getRootDiscussionEntries() {
        return root_discussion_entries;
    }
    public int getAttempt() {
        return attempt;
    }
    public String getBody() {
        return body;
    }

    public String getGrade() {
        return grade;
    }

    public boolean gradeMatchesCurrentSubmission() {
        return grade_matches_current_submission;
    }
    public Date getGradedAt() {
        if (gradedAtDate == null) {
            gradedAtDate = APIHelpers.stringToDate(graded_at);
        }
        return gradedAtDate;
    }
    public long getGraderId() {
        return grader_id;
    }
    public double getScore() {
        return score;
    }
    public String getSubmissionType() {
        return submission_type;
    }
    public Date getSubmittedAt() {
        if (submittedAtDate == null) {
            submittedAtDate = APIHelpers.stringToDate(submitted_at);
        }
        return submittedAtDate;
    }

    public String getWorkflowState() {
        return workflow_state;
    }

    public boolean isLate() {
        return late;
    }

    public String getPreviewUrl() {
        return preview_url;
    }

    public SubmissionComment[] getSubmissionComments() {
        return submission_comments;
    }

    public CanvasContext getCanvasContext() {
        return canvasContext;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public long getUser_id() {
        return user_id;
    }

    public User getUser() {
        return user;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public boolean isChecked() {
        return isChecked;
    }
    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    private Type typeFromString(String type) {
        if(type.toLowerCase().equals("conversation")) {
            return Type.CONVERSATION;
        } else if(type.toLowerCase().equals("submission")) {
            return Type.SUBMISSION;
        } else if(type.toLowerCase().equals("discussiontopic")) {
            return Type.DISCUSSION_TOPIC;
        } else if (type.toLowerCase().equals("announcement")){
            return Type.ANNOUNCEMENT;
        } else if(type.toLowerCase().equals("message")) {
            return Type.MESSAGE;
        } else if(type.toLowerCase().equals("conference")) {
            return Type.CONFERENCE;
        } else if(type.toLowerCase().equals("webconference")) {
            return Type.CONFERENCE;
        } else if(type.toLowerCase().equals("collaboration")) {
            return Type.COLLABORATION;
        } else if(type.toLowerCase().equals("collectionitem")) {
            return Type.COLLECTION_ITEM;
        }
        return Type.UNKNOWN;
    }

    public void setConversation(Context context, Conversation conversation, long myUserId, String monologueDefault) {
        this.conversation = conversation;
        title = conversation.getMessageTitle(myUserId,monologueDefault);
        message = createMessage(context);
    }

    public void setCanvasContextFromMap(Map<Long, Course> courseMap, Map<Long, Group> groupMap) {
        if (getContextType() == CanvasContext.Type.COURSE) {
            canvasContext = courseMap.get(getCourseId());
        } else {
            canvasContext = groupMap.get(getGroupId());
        }
    }

    private String createMessage(Context context) {
        switch (getType()) {
            case CONVERSATION:
                if (conversation == null) {
                    return context.getString(R.string.Loading);
                } else if (conversation.getLastMessagePreview() == null) {
                    return "";
                }
                return conversation.getLastMessagePreview();
            case SUBMISSION:
                //get comments from assignment
                String comment = null;
                if (submission_comments.length > 0) {
                    comment = submission_comments[submission_comments.length - 1].getComment();
                }
                //set it to the last comment if it's not null
                if(comment != null && !comment.equals("null") && score != -1.0) {
                    return (":" + score + " " + comment);
                }
                else if((comment == null || comment.equals("null")) && score != -1.0){
                    return (":" + score);
                }
                else if(comment != null && !comment.equals("null") && score == -1.0) {
                    return (comment);
                }
                break;
            case DISCUSSION_TOPIC :
                //if it's a discussionTopic, get the last entry for the message.
                if (root_discussion_entries.length > 0) {
                    return root_discussion_entries[root_discussion_entries.length - 1].getMessage(context.getString(R.string.Deleted));
                }
                break;
            default:
                break;
        }

        if (message == null) {
            return "";
        }
        return message;
    }

    private long parseAssignmentId() {
        //get the assignment from the url
        if(html_url.length() > 0 && !html_url.equals("null")) {
            String searchFor = "assignments/";
            int start = html_url.indexOf(searchFor);
            if (start == -1) {
                return 0;
            }
            start += searchFor.length();
            int end = html_url.indexOf("/", start);
            //in some urls the assignmentID might be the last thing so there wouldn't be a final /
            if(end == -1) {
                end = html_url.length();
            }
            String assignmentId = html_url.substring(start,end);

            return Long.parseLong(assignmentId);
        }
        return 0;
    }

    private long parseCourseId() {
        if(html_url.length() > 0 && !html_url.equals("null")) {
            String searchFor = "courses/";
            int start = html_url.indexOf(searchFor);
            if (start == -1) {
                return 0;
            }
            start += searchFor.length();
            int end = html_url.indexOf("/", start);

            String courseIdString = html_url.substring(start,end);

            return Long.parseLong(courseIdString);
        }
        return 0;
    }

    private long parseGroupId() {
        if(html_url.length() > 0 && !html_url.equals("null")) {
            String searchFor = "groups/";
            int start = html_url.indexOf(searchFor);
            if (start == -1) {
                return 0;
            }
            start += searchFor.length();
            int end = html_url.indexOf("/", start);

            String groupIdString = html_url.substring(start,end);

            return Long.parseLong(groupIdString);
        }
        return 0;
    }
}
