package com.instructure.canvasapi.model;


import android.os.Parcel;
import com.instructure.canvasapi.utilities.APIHelpers;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This is a (hopefully) temporary object in order to deal with include Observed_Users
 * This is necessary because the submission object with the above flag listed becomes a list
 * but maintains the same name "Submission"
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class AssignmentWithListSubmission extends CanvasModel<AssignmentWithListSubmission>{

    private long id;
    private String name;
    private String description;
    private List<String> submission_types = new ArrayList<String>();
    private String due_at;
    private double points_possible;
    private long course_id;

    private String grading_type;
    private long needs_grading_count;

    private String html_url;
    private String url;
    private long quiz_id; // (Optional) id of the associated quiz (applies only when submission_types is ["online_quiz"])
    private List<RubricCriterion> rubric = new ArrayList<RubricCriterion>();
    private boolean use_rubric_for_grading;
    private List<String> allowed_extensions = new ArrayList<String>();

    public List<Submission> getSubmission() {
        return submission;
    }

    public void setSubmission(List<Submission> submission) {
        this.submission = submission;
    }

    private List<Submission> submission = new ArrayList<>();
    private long assignment_group_id;
    private int position;
    private boolean peer_reviews;

    //Module lock info
    private LockInfo lock_info;
    private boolean locked_for_user;
    private String lock_at; //Date the teacher no longer accepts submissions.
    private String unlock_at;
    private String lock_explanation;

    private DiscussionTopicHeader discussion_topic;

    private List<NeedsGradingCount> needs_grading_count_by_section = new ArrayList<NeedsGradingCount>();

    private boolean free_form_criterion_comments;
    private boolean published;
    private boolean muted;
    private long group_category_id;

    private List<AssignmentDueDate> all_dates = new ArrayList<AssignmentDueDate>();

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
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
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
    public void setDueDate(Date dueDate){
        setDueDate(APIHelpers.dateToString(dueDate));
    }
    public void setLockAtDate(String lockAtDate){
        this.lock_at = lockAtDate;
    }

    public void setSubmissionTypes(ArrayList<String> submissionTypes) {
        if(submissionTypes == null){
            return;
        }

        this.submission_types = submissionTypes;
    }

    public long getNeedsGradingCount() {return needs_grading_count;}
    public void setNeedsGradingCount(long needs_grading_count) { this.needs_grading_count = needs_grading_count; }

    public double getPointsPossible() {
        return points_possible;
    }
    public void setPointsPossible(double pointsPossible) {
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
    public List<RubricCriterion> getRubric() {
        return rubric;
    }
    public void setRubric(List<RubricCriterion> rubric) {
        this.rubric = rubric;
    }
    public boolean isUseRubricForGrading() {
        return use_rubric_for_grading;
    }
    public void setUseRubricForGrading(boolean useRubricForGrading) {
        this.use_rubric_for_grading = useRubricForGrading;
    }
    public List<String> getAllowedExtensions() {
        return allowed_extensions;
    }
    public void setAllowedExtensions(List<String> allowedExtensions) {
        this.allowed_extensions = allowedExtensions;
    }
    public long getAssignmentGroupId() {
        return assignment_group_id;
    }
    public void setAssignmentGroupId(Long assignmentGroupId) {
        this.assignment_group_id = assignmentGroupId == null ?0:assignmentGroupId;
    }
    public LockInfo getLockInfo() {
        return lock_info;
    }
    public void setLockInfo(LockInfo lockInfo) {
        this.lock_info = lockInfo;
    }
    public boolean isLockedForUser() {
        return locked_for_user;
    }
    public void setLockedForUser(boolean locked) {
        this.locked_for_user = locked;
    }

    public Date getUnlockAt() {
        if(unlock_at == null){
            return null;
        }
        return APIHelpers.stringToDate(unlock_at);
    }

    public void setUnlockAt(Date unlockAt){
        unlock_at = APIHelpers.dateToString(unlockAt);
    }

    public boolean hasPeerReviews() {
        return peer_reviews;
    }

    public void setPeerReviews(boolean peerReviews) {
        this.peer_reviews = peer_reviews;
    }

    public List<NeedsGradingCount> getNeedsGradingCountBySection(){
        return needs_grading_count_by_section;
    }

    public void setNeedsGradingCountBySection(List<NeedsGradingCount> needs_grading_count_by_section){
        this.needs_grading_count_by_section = needs_grading_count_by_section;
    }

    public boolean isFreeFormCriterionComments() {
        return free_form_criterion_comments;
    }

    public void setFreeFormCriterionComments(boolean free_form_criterion_comments) {
        this.free_form_criterion_comments = free_form_criterion_comments;
    }
    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public long getGroupCategoryId(){
        return this.group_category_id;
    }

    public void setGroupCategoryId(long groupId){
        this.group_category_id = groupId;
    }

    public List<AssignmentDueDate> getDueDates(){
        return this.all_dates;
    }

    public void setAllDates(List<AssignmentDueDate> all_dates){
        this.all_dates = all_dates;
    }

    public void setMuted(boolean isMuted){
        this.muted = isMuted;
    }

    public boolean isMuted(){
        return this.muted;
    }

    public String getLock_explanation() {
        return lock_explanation;
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

    public AssignmentWithListSubmission() {}


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

    public boolean hasRubric() {
        if (rubric == null) {
            return false;
        }
        return rubric.size() > 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeList(this.submission_types);
        dest.writeString(this.due_at);
        dest.writeDouble(this.points_possible);
        dest.writeLong(this.course_id);
        dest.writeString(this.grading_type);
        dest.writeString(this.html_url);
        dest.writeString(this.url);
        dest.writeLong(this.quiz_id);
        dest.writeList(this.rubric);
        dest.writeByte(use_rubric_for_grading ? (byte) 1 : (byte) 0);
        dest.writeList(this.allowed_extensions);
        dest.writeList(this.submission);
        dest.writeLong(this.assignment_group_id);
        dest.writeByte(peer_reviews ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.lock_info, flags);
        dest.writeString(this.lock_at);
        dest.writeString(this.unlock_at);
        dest.writeParcelable(this.discussion_topic, flags);
        dest.writeLong(this.needs_grading_count);
        dest.writeList(this.needs_grading_count_by_section);
        dest.writeByte(free_form_criterion_comments ? (byte) 1 : (byte) 0);
        dest.writeByte(published ? (byte) 1 : (byte) 0);
        dest.writeLong(this.group_category_id);
        dest.writeList(this.all_dates);
        dest.writeByte(this.muted ? (byte)1 : (byte) 0);
        dest.writeByte(this.locked_for_user ? (byte)1 : (byte) 0);
    }

    private AssignmentWithListSubmission(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.description = in.readString();

        in.readList(this.submission_types, String.class.getClassLoader());

        this.due_at = in.readString();
        this.points_possible = in.readDouble();
        this.course_id = in.readLong();
        this.grading_type = in.readString();
        this.html_url = in.readString();
        this.url = in.readString();
        this.quiz_id = in.readLong();

        in.readList(this.rubric, RubricCriterion.class.getClassLoader());

        this.use_rubric_for_grading = in.readByte() != 0;

        in.readList(this.allowed_extensions, String.class.getClassLoader());

        in.readList(this.submission, Submission.class.getClassLoader());
        this.assignment_group_id = in.readLong();
        this.peer_reviews = in.readByte() != 0;
        this.lock_info =  in.readParcelable(LockInfo.class.getClassLoader());
        this.lock_at = in.readString();
        this.unlock_at = in.readString();
        this.discussion_topic = in.readParcelable(DiscussionTopicHeader.class.getClassLoader());
        this.needs_grading_count = in.readLong();
        in.readList(this.needs_grading_count_by_section, NeedsGradingCount.class.getClassLoader());
        this.free_form_criterion_comments = in.readByte() != 0;
        this.published = in.readByte() != 0;
        this.group_category_id = in.readLong();
        in.readList(this.all_dates, AssignmentDueDate.class.getClassLoader());
        this.muted = in.readByte() != 0;
        this.locked_for_user = in.readByte() != 0;
    }

    public static Creator<AssignmentWithListSubmission> CREATOR = new Creator<AssignmentWithListSubmission>() {
        public AssignmentWithListSubmission createFromParcel(Parcel source) {
            return new AssignmentWithListSubmission(source);
        }

        public AssignmentWithListSubmission[] newArray(int size) {
            return new AssignmentWithListSubmission[size];
        }
    };
}
