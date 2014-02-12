package com.instructure.canvasapi.model;

import android.content.Context;
import android.text.format.DateFormat;
import com.instructure.canvasapi.R;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.DateHelpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Joshua Dutton
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

//could be a calendar event or an assignment
public class ScheduleItem extends CanvasModel<ScheduleItem> {

    public enum Type { TYPE_ASSIGNMENT, TYPE_CALENDAR, TYPE_SYLLABUS }

    // from api
	private String id;
	private String title;
    private String description;
	private String start_at;
    private String end_at;
    private boolean all_day;
    private String all_day_date;
    private String location_address;
    private String location_name;
    private String html_url;
    private String context_code;
    private String effective_context_code;

    // helper variables
    private CanvasContext.Type contextType;
    private long userId = -1;
    private String userName;
    private long courseId = -1;
    private long groupId = -1;
	private Type type = Type.TYPE_CALENDAR;

	private List<String> submissionTypes = new ArrayList<String>();
	private double pointsPossible;
	private long quizId = 0;
	private DiscussionTopicHeader discussionTopicHeader;
	private String lockedModuleName;
    private Assignment assignment;


    // helper variables
    Date startDate;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        //id can either be a regular long, or it could be prefixed by "assignment_".
        //for more info check out the upcoming_events api documentation
        try {
            long tempId = Long.parseLong(id);
            return tempId;
        }
        catch(NumberFormatException e) {
            //it's a string with assignment_ as a prefix...hopefully
            try {
                String stringId = id.toString();
                String tempId = stringId.replace("assignment_", "");
                long assignmentId = Long.parseLong(tempId);
                setId(assignmentId);
                return assignmentId;
            }
            catch (Exception e1) {
                setId(-1);
                return -1L;
            }
        }
        catch(Exception e) {
            setId(-1);
            return -1L;
        }
    }
    public void setId(long id) {
        this.id = Long.toString(id);
    }
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public boolean isAllDay() {
        return all_day;
    }
    public void setAllDay(boolean allDay) {
        this.all_day = allDay;
    }
    public Date getAllDayDate() {
        if(all_day_date == null) {
            return null;
        }
        return APIHelpers.stringToDate(all_day_date);
    }
    public void setAllDayDate(String allDayDate) {
        this.all_day_date = allDayDate;
    }
    public String getLocationAddress() {
        return location_address;
    }
    public void setLocationAddress(String locationAddress) {
        this.location_address = locationAddress;
    }
    public String getLocationName() {
        return location_name;
    }
    public void setLocationName(String locationName) {
        this.location_name = locationName;
    }
    public CanvasContext.Type getContextType() {
        if(context_code == null) {
            contextType = CanvasContext.Type.USER;
        } else if (contextType == null) {
            parseContextCode();
        }

        return contextType;
    }
    public void setContextType(CanvasContext.Type contextType) {
        this.contextType = contextType;
    }
    public long getUserId() {
        if (userId < 0) {
            parseContextCode();
        }
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public long getCourseId() {
        if (courseId < 0) {
            parseContextCode();
        }
        return courseId;
    }
    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    public long getGroupId() {
        if (groupId < 0) {
            parseContextCode();
        }
        return groupId;
    }
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
    public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
    public Date getStartDate() {
        if(start_at == null) {
            return null;
        }

        if (startDate == null) {
            startDate = APIHelpers.stringToDate(start_at);
        }
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
        start_at = APIHelpers.dateToString(startDate);
    }
    public Date getEndDate() {
        if(end_at == null) {
            return null;
        }
        return APIHelpers.stringToDate(end_at);
    }
    public void setEndDate(String endDate) {
        this.end_at = endDate;
    }
	public List<String> getSubmissionTypes() {
		return submissionTypes;
	}
	public void setSubmissionTypes(List<String> submissionTypes) {
		this.submissionTypes = submissionTypes;
	}
	public double getPointsPossible() {
		return pointsPossible;
	}
	public void setPointsPossible(double pointsPossible) {
		this.pointsPossible = pointsPossible;
	}
	public void setDiscussionTopicHeader(DiscussionTopicHeader header) {
		discussionTopicHeader = header;
	}
	public DiscussionTopicHeader getDiscussionTopicHeader() {
		return discussionTopicHeader;
	}
	public String getHtmlUrl() {
		return html_url;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.html_url = htmlUrl;
	}
	public long getQuizId() {
		return quizId;
	}
	public void setQuizId(long id) {
		quizId = id;
	}
	public String getLockedModuleName() {
        return lockedModuleName;
    }
    public void setLockedModuleName(String lockedModuleName) {
        this.lockedModuleName = lockedModuleName;
    }
    public Assignment getAssignment() {
        return assignment;
    }
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return getStartDate();
    }

    @Override
    public String getComparisonString() {
        return getTitle();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public ScheduleItem() {}

    public ScheduleItem(CanvasContext.Type type) {
        setContextType(type);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public long getContextId() {
        switch (getContextType()) {
            case COURSE:
                return getCourseId();
            case GROUP:
                return getGroupId();
            case USER:
                return getUserId();
            default:
                return -1;
        }
    }

    private void parseContextCode() {
        if (effective_context_code != null) {
            parseContextCode(effective_context_code);
        } else {
            parseContextCode(context_code);
        }
    }

    private void parseContextCode(String contextCode) {
        if (contextCode.startsWith("user_")) {
            setContextType(CanvasContext.Type.USER);
            String userId = contextCode.replace("user_", "");
            setUserId(Long.parseLong(userId));
        } else if (contextCode.startsWith("course_")) {
            setContextType(CanvasContext.Type.COURSE);
            String courseId = contextCode.replace("course_", "");
            setCourseId(Long.parseLong(courseId));
        } else if (contextCode.startsWith("group_")) {
            setContextType(CanvasContext.Type.GROUP);
            String groupId = contextCode.replace("group_", "");
            setGroupId(Long.parseLong(groupId));
        }
    }

    public String getStartString(Context context) {
        if (isAllDay()) {
            return context.getString(R.string.allDayEvent);
        }
        if (getStartDate() != null) {
            return DateHelpers.createDateTimeString(context, context.getString(R.string.Starts), getStartDate());
        }
        return "";
    }

    public String getShortStartString(Context context) {
        if (isAllDay() && getAllDayDate() != null) {
            return DateFormat.format("MMM d", getAllDayDate()).toString();
        }
        if (getStartDate() != null) {
            return DateHelpers.createShortDateTimeString(context, getStartDate());
        }
        return "";
    }

    public String getStartToEndString(Context context) {
        if (isAllDay()) {
            return context.getString(R.string.allDayEvent);
        }
        if (getStartDate() != null) {
            if (getEndDate() != null && !getStartDate().equals(getEndDate())) {
                return DateHelpers.createTimeString(getStartDate()) + " " + context.getString(R.string.to) +  " " + DateHelpers.createTimeString(getEndDate());
            }
            return DateHelpers.createTimeString(getStartDate());
        }
        return "";
    }

    public String getEndString(Context context) {
        if (isAllDay()) {
            if (getAllDayDate() != null) {
                return DateFormat.format("MMM d, yyyy", getAllDayDate()).toString();
            } else if (getStartDate() != null) {
                return DateFormat.format("MMM d, yyyy", getStartDate()).toString();
            }
        }
        if (getEndDate() != null) {
            return DateHelpers.createDateTimeString(context, context.getString(R.string.Ends), getEndDate());
        }
        return "";
    }

    public int getStartDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getStartDate());

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static ScheduleItem createSyllabus(String title, String description) {
        ScheduleItem syllabus = new ScheduleItem();
        syllabus.setType(Type.TYPE_SYLLABUS);
        syllabus.setTitle(title);
        syllabus.setDescription(description);
        syllabus.setId(Long.MIN_VALUE);

        return syllabus;
    }
}
