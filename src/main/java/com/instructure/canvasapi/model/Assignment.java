package com.instructure.canvasapi.model;


import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class Assignment extends CanvasModel<Assignment> {

	private long id;
	private String name;
	private String description;
	private String[] submission_types = new String[0];
	private String due_at;
	private double points_possible;
	private long course_id;

	private String html_url;
    private String url;

    private long quiz_id; // (Optional) id of the associated quiz (applies only when submission_types is ["online_quiz"])
    private RubricCriterion[] rubric;
    private boolean use_rubric_for_grading;
	private String[] allowed_extensions = new String[0];
    private Submission submission;
    private long assignment_group_id;
    private LockInfo lock_info;

    private DiscussionTopicHeader discussion_topic;

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
        if(due_at == null) {
            return null;
        }
		return APIHelpers.stringToDate(due_at);
	}
	public void setDueDate(String dueDate) {
		this.due_at = dueDate;
	}
	public List<String> getSubmissionTypes() {
        if(submission_types == null) {
            return new ArrayList<String>();
        }
		return Arrays.asList(submission_types);
	}
	public void setSubmissionTypes(String[] submissionTypes) {
		this.submission_types = submissionTypes;
	}
	public double getPointsPossible() {
		return points_possible;
	}
	public void setPointsPossible(int pointsPossible) {
		this.points_possible = pointsPossible;
	}
	public long getCourseId() {
		return course_id;
	}
	public void setCourseId(long courseId) {
		this.course_id = courseId;
	}
    public void setDiscussionTopicHeader(DiscussionTopicHeader header) {
        discussion_topic = header;
    }
    public DiscussionTopicHeader getDiscussionTopicHeader() {
        return discussion_topic;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
	public String getHtmlUrl() {
		return html_url;
	}
	public void setHtmlUrl(String htmlUrl) {
		this.html_url = htmlUrl;
	}
    public long getQuizId() {
        return quiz_id;
    }
    public void setQuizId(long id) {
        quiz_id = id;
    }
    public RubricCriterion[] getRubric() {
        return rubric;
    }
    public void setRubric(RubricCriterion[] rubric) {
        this.rubric = rubric;
    }
    public boolean isUseRubricForGrading() {
        return use_rubric_for_grading;
    }
    public void setUseRubricForGrading(boolean useRubricForGrading) {
        this.use_rubric_for_grading = useRubricForGrading;
    }
    public List<String> getAllowedExtensions() {
        return Arrays.asList(allowed_extensions);
    }
    public void setAllowedExtensions(String[] allowedExtensions) {
        this.allowed_extensions = allowedExtensions;
    }
    public Submission getLastSubmission() {
        return submission;
    }
    public void setLastSubmission(Submission lastSubmission) {
        this.submission = lastSubmission;
    }
    public long getAssignmentGroupId() {
        return assignment_group_id;
    }
    public void setAssignmentGroupId(long assignmentGroupId) {
        this.assignment_group_id = assignmentGroupId;
    }
    public LockInfo getLockInfo() {
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
        return getDueDate();
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Assignment() {}
	

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public boolean isLocked() {
        Date currentDate = new Date();
        if(getLockInfo() == null || getLockInfo().isEmpty()) {
            return false;
        } else if(getLockInfo().getLockedModuleName() != null && getLockInfo().getLockedModuleName().length() > 0 && !getLockInfo().getLockedModuleName().equals("null")) {
            return true;
        } else if(getLockInfo().getUnlockedAt().after(currentDate)){
            return true;
        }
        return false;

    }

	public void populateScheduleItem(ScheduleItem scheduleItem) {
        scheduleItem.setId(this.getId());
        scheduleItem.setTitle(this.getName());
        scheduleItem.setStartDate(this.getDueDate());
        scheduleItem.setType(ScheduleItem.Type.TYPE_ASSIGNMENT);
        scheduleItem.setDescription(this.getDescription());
        scheduleItem.setSubmissionTypes(getSubmissionTypes());
        scheduleItem.setPointsPossible(this.getPointsPossible());
        scheduleItem.setHtmlUrl(this.getHtmlUrl());
        scheduleItem.setQuizId(this.getQuizId());
        scheduleItem.setDiscussionTopicHeader(this.getDiscussionTopicHeader());
        scheduleItem.setAssignment(this);
        if(getLockInfo() != null && getLockInfo().getLockedModuleName() != null) {
            scheduleItem.setLockedModuleName(this.getLockInfo().getLockedModuleName());
        }
    }

    public ScheduleItem toScheduleItem() {
		ScheduleItem scheduleItem = new ScheduleItem();

		populateScheduleItem(scheduleItem);

		return scheduleItem;
	}

    public boolean hasRubric() {
        if (rubric == null) {
            return false;
        }
        return rubric.length > 0;
    }
}
