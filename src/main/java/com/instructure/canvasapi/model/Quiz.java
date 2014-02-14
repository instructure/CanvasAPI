package com.instructure.canvasapi.model;

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
}
