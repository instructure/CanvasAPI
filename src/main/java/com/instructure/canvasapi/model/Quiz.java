package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * Created by Wesley Smith on 6/10/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Quiz extends CanvasModel<Quiz> {

    // constants

    public final static String TYPE_PRACTICE = "practice_quiz";
    public final static String TYPE_ASSIGNMENT = "assignment";
    public final static String TYPE_GRADED_SURVEY = "graded_survey";
    public final static String TYPE_SURVEY = "survey";

    // API variables

    private long id;
    private String title;
    private String mobile_url;
    private String html_url;

    private String description;
    private String quiz_type;
    private LockInfo lock_info;
    private QuizPermission permissions;
    private int allowed_attempts;
    private int question_count;
    private int points_possible;
    private String due_at;
    private int time_limit;

    // Helper variables

    private Assignment assignment;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {

        if (mobile_url != null && !mobile_url.equals("")) {
            return mobile_url;
        }
        return html_url;
    }

    public String getDescription() {
        if (description != null) {
            return description;
        }
        return "";
    }

    public String getType() {
        return quiz_type;
    }

    //During parsing, GSON will try. Which means sometimes we get 'empty' objects
    //They're non-null, but don't have any information.
    public LockInfo getLockInfo() {
        if(lock_info == null || lock_info.isEmpty()){
            return null;
        }
        return lock_info;
    }

    public void setLockInfo(LockInfo lockInfo) {
        this.lock_info = lockInfo;
    }

    public int getAllowedAttempts() {
        return allowed_attempts;
    }

    public void setAllowedAttempts(int allowed_attempts) {
        this.allowed_attempts = allowed_attempts;
    }

    public int getQuestionCount() {
        return question_count;
    }

    public void setQuestionCount(int question_count) {
        this.question_count = question_count;
    }

    public int getPointsPossible() {
        return points_possible;
    }

    public void setPointsPossible(int points_possible) {
        this.points_possible = points_possible;
    }

    public Date getDueAt() {
        return APIHelpers.stringToDate(due_at);
    }

    public void setDueAt(String due_at) {
        this.due_at = due_at;
    }

    public int getTimeLimit() {
        return time_limit;
    }

    public void setTimeLimit(int time_limit) {
        this.time_limit = time_limit;
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
        return null;
    }

    @Override
    public String getComparisonString() {
        if (getAssignment() != null) {
            return getAssignment().getName();
        }
        return getTitle();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.mobile_url);
        dest.writeString(this.html_url);
        dest.writeString(this.description);
        dest.writeString(this.quiz_type);
        dest.writeParcelable(this.lock_info, flags);
        dest.writeParcelable(this.assignment, flags);
        dest.writeParcelable(this.permissions, flags);
        dest.writeInt(this.allowed_attempts);
        dest.writeInt(this.question_count);
        dest.writeInt(this.points_possible);
        dest.writeString(this.due_at);
    }

    public Quiz() {
    }

    private Quiz(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.mobile_url = in.readString();
        this.html_url = in.readString();
        this.description = in.readString();
        this.quiz_type = in.readString();
        this.lock_info =  in.readParcelable(LockInfo.class.getClassLoader());
        this.assignment = in.readParcelable(Assignment.class.getClassLoader());
        this.permissions = in.readParcelable(QuizPermission.class.getClassLoader());
        this.allowed_attempts = in.readInt();
        this.question_count = in.readInt();
        this.points_possible = in.readInt();
        this.due_at = in.readString();
    }

    public static Creator<Quiz> CREATOR = new Creator<Quiz>() {
        public Quiz createFromParcel(Parcel source) {
            return new Quiz(source);
        }

        public Quiz[] newArray(int size) {
            return new Quiz[size];
        }
    };
}
