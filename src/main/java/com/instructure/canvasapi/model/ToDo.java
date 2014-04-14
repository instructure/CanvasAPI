package com.instructure.canvasapi.model;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by Joshua Dutton
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class ToDo extends CanvasComparable<ToDo> {

    public enum Type implements Serializable { SUBMITTING, GRADING, UPCOMING_EVENT, UPCOMING_ASSIGNMENT }

    // member variables
    private String start_date;
	private String type;
	private int needs_grading_count;
	private String ignore;
	private String ignore_permanently;
	private String html_url;
    private long course_id;
    private long group_id;

	// helper variables
    private Date startDate;
    private Type typeEnum = null;
	private CanvasContext canvasContext;
    private Assignment assignment;

    private ScheduleItem scheduleItem;
    private boolean checked;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public Date getStartDate() {
        if (startDate == null) {
            startDate = APIHelpers.stringToDate(start_date);
        }
        return startDate;
    }
	public Type getType() {
        if (typeEnum == null) {
            if(type.equals("submitting")) {
                typeEnum = Type.SUBMITTING;
            }
            else if (type.equals("grading")) {
                typeEnum = Type.GRADING;
            } else if(assignment != null) {
                typeEnum = Type.UPCOMING_ASSIGNMENT;
            } else {
                typeEnum = Type.UPCOMING_EVENT;
            }
        }
		return typeEnum;
	}
	public void setType(Type type) {
		this.typeEnum = type;
	}
	public int getNeedsGradingCount() {
		return needs_grading_count;
	}
	public void setNeedsGradingCount(int needs_grading_count) {
		this.needs_grading_count = needs_grading_count;
	}
	public String getIgnore() {
		return ignore;
	}
	public void setIgnore(String ignore) {
		this.ignore = ignore;
	}
	public String getIgnorePermanently() {
		return ignore_permanently;
	}
	public void setIgnorePermanently(String ignore_permanently) {
		this.ignore_permanently = ignore_permanently;
	}
	public String getHtmlUrl() {
		return html_url;
	}
	public void setHtmlUrl(String html_url) {
		this.html_url = html_url;
	}
	public void setCourseId(long courseId) {
		this.course_id = courseId;
	}

    public CanvasContext getCanvasContext() {
        return canvasContext;
    }

    public void setCanvasContext(CanvasContext canvasContext) {
        this.canvasContext = canvasContext;
    }
    public ScheduleItem getScheduleItem() {
        return scheduleItem;
    }
    public void setScheduleItem(ScheduleItem scheduleItem) {
        this.scheduleItem = scheduleItem;
    }
    public Assignment getAssignment() {
        return assignment;
    }
    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public ToDo() {}

    /* Example JSON Response
     *
    [
      {
        'type': 'grading',        // an assignment that needs grading
        'assignment': { .. assignment object .. },
        'ignore': '.. url ..',
        'ignore_permanently': '.. url ..',
        'html_url': '.. url ..',
        'needs_grading_count': 3, // number of submissions that need grading
        'context_type': 'course', // course|group
        'course_id': 1,
        'group_id': null,
      },
      {
        'type' => 'submitting',   // an assignment that needs submitting soon
        'assignment' => { .. assignment object .. },
        'ignore' => '.. url ..',
        'ignore_permanently' => '.. url ..',
        'html_url': '.. url ..',
        'context_type': 'course',
        'course_id': 1,
      }
    ]
    */

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        Date date = new Date(Long.MAX_VALUE);
        if (getStartDate() != null) {
            date = getStartDate();
        }

        // due date is more important if we have one
        if (getDueDate() != null) {
            date = getDueDate();
        }
        return date;
    }

    @Override
    public String getComparisonString() {
        return getTitle();
    }

    @Override
    public int hashCode() {
        return getComparisonString().hashCode() + getComparisonDate().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        try {
            ToDo toDo = (ToDo) o;
            return getComparisonString().equals(toDo.getComparisonString()) && getComparisonDate().equals(toDo.getComparisonDate());
        } catch (Exception e) {
            return false;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public String getTitle() {
        if (getAssignment() != null && assignment.getName() != null) {
            return assignment.getName();
        } else if (getScheduleItem() != null && getScheduleItem().getTitle() != null) {
            return getScheduleItem().getTitle();
        }
        return "";
    }
    public Date getDueDate() {
        if (assignment != null) {
            return assignment.getDueDate();
        } else {
            return scheduleItem.getEndDate();
        }
    }


    public static void setContextInfo(ToDo toDo, Map<Long, Course> courses, Map<Long, Group> groups) {
        if(toDo.group_id > 0){
            toDo.canvasContext = groups.get(toDo.group_id);
        } else {
            toDo.canvasContext = courses.get(toDo.course_id);
        }
    }

    public static ToDo toDoWithScheduleItem(ScheduleItem scheduleItem) {
        ToDo toDo = new ToDo();

        toDo.setScheduleItem(scheduleItem);
        if (scheduleItem.getAssignment() == null) {
            toDo.setType(Type.UPCOMING_EVENT);
        } else {
            toDo.setAssignment(scheduleItem.getAssignment());
            toDo.setType(Type.UPCOMING_ASSIGNMENT);
        }

        if (scheduleItem.getContextType() == CanvasContext.Type.COURSE) {
            toDo.setCourseId(scheduleItem.getCourseId());
        } else if (scheduleItem.getContextType() == CanvasContext.Type.GROUP) {
            toDo.group_id = scheduleItem.getGroupId();
        }

        return toDo;
    }
}
