package com.instructure.canvasapi.model;


import android.content.Context;

import com.instructure.canvasapi.R;
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

    private String grading_type;

	private String html_url;
    private String url;

    private long quiz_id; // (Optional) id of the associated quiz (applies only when submission_types is ["online_quiz"])
    private RubricCriterion[] rubric;
    private boolean use_rubric_for_grading;
	private String[] allowed_extensions = new String[0];
    private Submission submission;
    private long assignment_group_id;

    //Module lock info
    private LockInfo lock_info;

    //Date the teacher no longer accepts submissions.
    private String lock_at;

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
    public Date getlockAtDate(){
        if(lock_at == null){
            return null;
        }
        return APIHelpers.stringToDate(lock_at);
    }
	public void setDueDate(String dueDate) {
		this.due_at = dueDate;
	}
    public void setLockAtDate(String lockAtDate){
        this.lock_at = lockAtDate;
    }
	public List<SUBMISSION_TYPE> getSubmissionTypes() {
        if(submission_types == null) {
            return new ArrayList<SUBMISSION_TYPE>();
        }

        ArrayList<SUBMISSION_TYPE>   submissionTypeList = new ArrayList<SUBMISSION_TYPE>();

        for(String submissionType : submission_types){
            submissionTypeList.add(getSubmissionTypeFromAPIString(submissionType));
        }

		return submissionTypeList;
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
    public GRADING_TYPE getGradingType(Context context){return getGradingTypeFromString(grading_type, context);}
    public TURN_IN_TYPE getTurnInType(){return turnInTypeFromSubmissionType(getSubmissionTypes());}

    public Submission getLastActualSubmission() {
        if(submission == null) {
            return null;
        }
        if(submission.getWorkflowState() != null && submission.getWorkflowState().equals("submitted")) {
            return submission;
        }
        else {
            return null;
        }
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
    public static final SUBMISSION_TYPE[] ONLINE_SUBMISSIONS = {SUBMISSION_TYPE.ONLINE_UPLOAD, SUBMISSION_TYPE.ONLINE_URL, SUBMISSION_TYPE.ONLINE_TEXT_ENTRY, SUBMISSION_TYPE.MEDIA_RECORDING};


    public enum TURN_IN_TYPE {ONLINE, ON_PAPER, NONE, DISCUSSION, QUIZ, EXTERNAL_TOOL}

    public static TURN_IN_TYPE stringToTurnInType(String turnInType, Context context){
        if(turnInType == null){
            return null;
        }

        if(turnInType.equals(context.getString(R.string.canvasAPI_online))){
            return TURN_IN_TYPE.ONLINE;
        } else if(turnInType.equals(context.getString(R.string.canvasAPI_onPaper))){
            return TURN_IN_TYPE.ON_PAPER;
        } else if(turnInType.equals(context.getString(R.string.canvasAPI_discussion))){
            return TURN_IN_TYPE.DISCUSSION;
        } else if(turnInType.equals(context.getString(R.string.canvasAPI_quiz))){
            return TURN_IN_TYPE.QUIZ;
        } else if(turnInType.equals(context.getString(R.string.canvasAPI_externalTool))){
            return TURN_IN_TYPE.EXTERNAL_TOOL;
        } else{
            return TURN_IN_TYPE.NONE;
        }

    }

    public static String turnInTypeToPrettyPrintString(TURN_IN_TYPE turnInType, Context context){
        if(turnInType == null){
            return null;
        }

        switch (turnInType){
            case ONLINE:
                return context.getString(R.string.canvasAPI_online);
            case ON_PAPER:
                return context.getString(R.string.canvasAPI_onPaper);
            case NONE:
                return context.getString(R.string.canvasAPI_none);
            case DISCUSSION:
                return context.getString(R.string.canvasAPI_discussion);
            case QUIZ:
                return context.getString(R.string.canvasAPI_quiz);
            case EXTERNAL_TOOL:
                return context.getString(R.string.canvasAPI_externalTool);
            default:
                return null;
        }
    }

    private TURN_IN_TYPE turnInTypeFromSubmissionType(List<SUBMISSION_TYPE> submissionTypes){

        if(submissionTypes == null || submissionTypes.size() == 0){
            return TURN_IN_TYPE.NONE;
        }

        SUBMISSION_TYPE submissionType = submissionTypes.get(0);

        if(submissionType == SUBMISSION_TYPE.MEDIA_RECORDING || submissionType == SUBMISSION_TYPE.ONLINE_TEXT_ENTRY ||
                submissionType == SUBMISSION_TYPE.ONLINE_URL || submissionType == SUBMISSION_TYPE.ONLINE_UPLOAD ){
            return TURN_IN_TYPE.ONLINE;
        }else if(submissionType == SUBMISSION_TYPE.ONLINE_QUIZ){
            return TURN_IN_TYPE.QUIZ;
        }else if(submissionType == SUBMISSION_TYPE.DISCUSSION_TOPIC){
            return TURN_IN_TYPE.DISCUSSION;
        }else if(submissionType == SUBMISSION_TYPE.ON_PAPER){
            return TURN_IN_TYPE.ON_PAPER;
        }else if(submissionType == SUBMISSION_TYPE.EXTERNAL_TOOL){
            return TURN_IN_TYPE.EXTERNAL_TOOL;
        }

        return TURN_IN_TYPE.NONE;
    }

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

    public enum SUBMISSION_TYPE {ONLINE_QUIZ, NONE, ON_PAPER, DISCUSSION_TOPIC, EXTERNAL_TOOL, ONLINE_UPLOAD, ONLINE_TEXT_ENTRY, ONLINE_URL, MEDIA_RECORDING}

    private SUBMISSION_TYPE getSubmissionTypeFromAPIString(String submissionType){
        if(submissionType.equals("online_quiz")){
            return SUBMISSION_TYPE.ONLINE_QUIZ;
        } else if(submissionType.equals("none")){
            return SUBMISSION_TYPE.NONE;
        } else if(submissionType.equals("on_paper")){
            return SUBMISSION_TYPE.ON_PAPER;
        } else if(submissionType.equals("discussion_topic")){
            return SUBMISSION_TYPE.DISCUSSION_TOPIC;
        } else if(submissionType.equals("external_tool")){
            return SUBMISSION_TYPE.EXTERNAL_TOOL;
        } else if(submissionType.equals("online_upload")){
            return SUBMISSION_TYPE.ONLINE_UPLOAD;
        } else if(submissionType.equals("online_text_entry")){
            return SUBMISSION_TYPE.ONLINE_TEXT_ENTRY;
        } else if(submissionType.equals("online_url")){
            return SUBMISSION_TYPE.ONLINE_URL;
        } else if(submissionType.equals("media_recording")){
            return SUBMISSION_TYPE.MEDIA_RECORDING;
        } else {
            return null;
        }
    }
    public static String submissionTypeToAPIString(SUBMISSION_TYPE submissionType){

        if(submissionType == null){
            return null;
        }

        switch (submissionType){
            case  ONLINE_QUIZ:
                return "online_quiz";
            case NONE:
                return "none";
            case ON_PAPER:
                return "on_paper";
            case DISCUSSION_TOPIC:
                return "discussion_topic";
            case EXTERNAL_TOOL:
                return "external_tool";
            case ONLINE_UPLOAD:
                return "online_upload";
            case ONLINE_TEXT_ENTRY:
                return "online_text_entry";
            case ONLINE_URL:
                return "online_url";
            case MEDIA_RECORDING:
                return "media_recording";
            default:
                return "";
        }
    }
    public static String submissionTypeToPrettyPrintString(SUBMISSION_TYPE submissionType, Context context){

        if(submissionType == null){
            return null;
        }

        switch (submissionType){
            case  ONLINE_QUIZ:
                return context.getString(R.string.canvasAPI_onlineQuiz);
            case NONE:
                return context.getString(R.string.canvasAPI_none);
            case ON_PAPER:
                return context.getString(R.string.canvasAPI_onPaper);
            case DISCUSSION_TOPIC:
                return context.getString(R.string.canvasAPI_discussionTopic);
            case EXTERNAL_TOOL:
                return context.getString(R.string.canvasAPI_externalTool);
            case ONLINE_UPLOAD:
                return context.getString(R.string.canvasAPI_onlineUpload);
            case ONLINE_TEXT_ENTRY:
                return context.getString(R.string.canvasAPI_onlineTextEntry);
            case ONLINE_URL:
                return context.getString(R.string.canvasAPI_onlineURL);
            case MEDIA_RECORDING:
                return context.getString(R.string.canvasAPI_mediaRecording);
            default:
                return "";
        }
    }

    public enum GRADING_TYPE {PASS_FAIL, PERCENT, LETTER_GRADE, POINTS}

    public static GRADING_TYPE getGradingTypeFromString(String gradingType, Context context){
        if(gradingType.equals("pass_fail") || gradingType.equals(context.getString(R.string.canvasAPI_passFail))){
            return GRADING_TYPE.PASS_FAIL;
        } else if(gradingType.equals("percent") || gradingType.equals(context.getString(R.string.canvasAPI_percent))){
            return GRADING_TYPE.PERCENT;
        } else if(gradingType.equals("letter_grade") || gradingType.equals(context.getString(R.string.canvasAPI_letterGrade))){
            return GRADING_TYPE.LETTER_GRADE;
        } else if (gradingType.equals("points") || gradingType.equals(context.getString(R.string.canvasAPI_points))){
            return GRADING_TYPE.POINTS;
        } else {
            return null;
        }
    }

    public  static String gradingTypeToAPIString(GRADING_TYPE gradingType){
        if(gradingType == null){ return null;}

        switch (gradingType){
            case PASS_FAIL:
                return "pass_fail";
            case PERCENT:
                return "percent";
            case LETTER_GRADE:
                return "letter_grade";
            case POINTS:
                return "points";
            default:
                return "";
        }
    }

    public  static String gradingTypeToPrettyPrintString(GRADING_TYPE gradingType, Context context){
        if(gradingType == null){ return null;}

        switch (gradingType){
            case PASS_FAIL:
                return context.getString(R.string.canvasAPI_passFail);
            case PERCENT:
                return context.getString(R.string.canvasAPI_percent);
            case LETTER_GRADE:
                return context.getString(R.string.canvasAPI_letterGrade);
            case POINTS:
                return context.getString(R.string.canvasAPI_points);
            default:
                return "";
        }
    }
}
