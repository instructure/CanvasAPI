package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Enrollment extends CanvasModel<Enrollment> implements Parcelable {

    // grades object when the enrollment is from a user
    private class Grades implements Serializable {

        private static final long serialVersionUID = 1L;

        private String html_url;
        private double current_score;
        private double final_score;
        private String current_grade;
        private String final_grade;
        }

    private static final long serialVersionUID = 1L;

    public Enrollment(){
        type = "";
    }


    private String role;
    private String type;

    // only included when we get enrollments using the user's url:
    // /users/self/enrollments
    private long id;
    private long course_id;
    private long course_section_id;
    private String enrollment_state;
    private long user_id;
    private Grades grades;

    // only included when we get the enrollment with a course object
    private double computed_current_score;
    private double computed_final_score;
    private String computed_current_grade;
    private String computed_final_grade;

    //The unique id of the associated user. Will be null unless type is
    //ObserverEnrollment.
    private long associated_user_id;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getType() {
        String enrollment = "enrollment";
        if(type.toLowerCase().endsWith(enrollment)){
            type = type.substring(0, type.length() - enrollment.length());
        }

        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    @Override
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getCourseId() {
        return course_id;
    }
    public void setCourseId(long course_id) {
        this.course_id = course_id;
    }
    public long getCourseSectionId() {
        return course_section_id;
    }
    public void setCourseSectionId(long course_section_id) {
        this.course_section_id = course_section_id;
    }
    public String getEnrollmentState() {
        return enrollment_state;
    }
    public void setEnrollmentState(String enrollment_state) {
        this.enrollment_state = enrollment_state;
    }
    public long getUserId() {
        return user_id;
    }
    public void setUserId(long user_id) {
        this.user_id = user_id;
    }
    public double getCurrentScore() {
        if (grades != null) {
            return grades.current_score;
        }
        return computed_current_score;
    }
    public double getFinalScore() {
        if (grades != null) {
            return grades.final_score;
        }
        return computed_final_score;
    }
    public String getCurrentGrade() {
        if (grades != null) {
            return grades.current_grade;
        }
        return computed_current_grade;
    }
    public String getFinalGrade() {
        if (grades != null) {
            return grades.final_grade;
        }
        return computed_final_grade;
    }

    public long getAssociated_user_id() {
        return associated_user_id;
    }

    public void setAssociated_user_id(long associated_user_id) {
        this.associated_user_id = associated_user_id;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public boolean isStudent() {
        if (type.equalsIgnoreCase("student") || type.equalsIgnoreCase("studentenrollment")) {
            return true;
        }
        return false;
    }

    public boolean isTeacher() {
        if (type.equalsIgnoreCase("teacher") || type.equalsIgnoreCase("teacherenrollment")) {
            return true;
        }
        return false;
    }

    public boolean isObserver() {
        if (type.equalsIgnoreCase("observer") || type.equalsIgnoreCase("observerenrollment")) {
            return true;
        }
        return false;
    }

    public boolean isTA() {
        if (type.equalsIgnoreCase("ta") || type.equalsIgnoreCase("taenrollment")) {
            return true;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enrollment that = (Enrollment) o;

        if (!type.equals(that.type)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.role);
        dest.writeString(this.type);
        dest.writeLong(this.id);
        dest.writeLong(this.course_id);
        dest.writeLong(this.course_section_id);
        dest.writeString(this.enrollment_state);
        dest.writeLong(this.user_id);
        dest.writeSerializable(this.grades);
        dest.writeDouble(this.computed_current_score);
        dest.writeDouble(this.computed_final_score);
        dest.writeString(this.computed_current_grade);
        dest.writeString(this.computed_final_grade);
        dest.writeLong(this.associated_user_id);
    }

    private Enrollment(Parcel in) {
        this.role = in.readString();
        this.type = in.readString();
        this.id = in.readLong();
        this.course_id = in.readLong();
        this.course_section_id = in.readLong();
        this.enrollment_state = in.readString();
        this.user_id = in.readLong();
        this.grades = (Grades) in.readSerializable();
        this.computed_current_score = in.readDouble();
        this.computed_final_score = in.readDouble();
        this.computed_current_grade = in.readString();
        this.computed_final_grade = in.readString();
        this.associated_user_id = in.readLong();
    }

    public static Parcelable.Creator<Enrollment> CREATOR = new Parcelable.Creator<Enrollment>() {
        public Enrollment createFromParcel(Parcel source) {
            return new Enrollment(source);
        }

        public Enrollment[] newArray(int size) {
            return new Enrollment[size];
        }
    };
}