package com.instructure.canvasapi.model;

import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class User extends CanvasModel<User> {

    private long id;
    private String name;
    private String short_name;
    private String login_id;
    private String avatar_url;
    private String primary_email;

    private Enrollment[] enrollments;

    //Helper variable for the "specified" enrollment.
    private int enrollmentIndex;

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

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
    public String getShortName() {
        return short_name;
    }
    public void setShortName(String shortName) {
        this.short_name = shortName;
    }
    public String getLoginId() {
        return login_id;
    }
    public void setLoginId(String loginId) {
        this.login_id = loginId;
    }
    public String getAvatarURL() {
        return avatar_url;
    }
    public void setAvatarURL(String avatar) {
        this.avatar_url = avatar;
    }
    public String getEmail() {
        return primary_email;
    }
    public void setEmail(String email) {
        this.primary_email = email;
    }
    public Enrollment[] getEnrollments() {
        return enrollments;
    }
    public void setEnrollments(Enrollment[] enrollments) {
        this.enrollments = enrollments;
    }
    public int getEnrollmentIndex(){
        return enrollmentIndex;
    }
    public void setEnrollmentIndex(int index){
        enrollmentIndex = index;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public User() {}

    public User(long id) {
        this.id = id;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        User other = (User) obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }
}
