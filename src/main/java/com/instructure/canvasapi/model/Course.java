package com.instructure.canvasapi.model;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by Joshua Dutton on 9/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Course extends CanvasContext implements Comparable<CanvasContext> {

    // Variables from API
    private long id;
    private String name;
    private String course_code;
    private String start_at;
    private String end_at;
    private String syllabus_body;
    private boolean hide_final_grades;
    private boolean is_public;
    private String license;
    private Term term;
    private Enrollment[] enrollments;

    // Helper variables
    private Double currentScore;
    private Double finalScore;
    private boolean checkedCurrentGrade;
    private boolean checkedFinalGrade;
    private String currentGrade;
    private String finalGrade;
    private boolean isFavorite;
    private boolean isFacingForward = true;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }

    @Override
    public Type getType(){return Type.COURSE;}

    @Override
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
    }


    public void setName(String name) {
        this.name = name;
    }
    public String getCourseCode() {
        return course_code;
    }
    public void setCourseCode(String courseCode) {
        course_code = courseCode;
    }
    public Date getStartDate() {
        return APIHelpers.stringToDate(start_at);
    }
    public Date getEndDate() {
        return APIHelpers.stringToDate(end_at);
    }
    public String getSyllabusBody() {
        return syllabus_body;
    }
    public void setSyllabusBody(String syllabusBody) {
        syllabus_body = syllabusBody;
    }
    public boolean isPublic() {
        return is_public;
    }
    public void setIsPublic(boolean isPublic) {
        this.is_public = isPublic;
    }
    public boolean isFinalGradeHidden() {
        return hide_final_grades;
    }

    public void setHideFinalGrades(boolean hide_final_grades) {
        this.hide_final_grades = hide_final_grades;
    }

    public Term getTerm() {
        return term;
    }
    public Enrollment[] getEnrollments() {
        return enrollments;
    }
    public String getHomePage() {
        return default_view;
    }
    public boolean isFavorite() {
        return isFavorite;
    }
    public void setFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
    /**
     * Not linked or updated from the API, internal use only.
     * @return
     */
    public boolean isFacingForward() {
        return isFacingForward;
    }
    /**
     * Not linked or updated from the API, internal use only.
     * @return
     */
    public void setFacingForward(boolean isFacingForward) {
        this.isFacingForward = isFacingForward;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Course() {}

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public boolean isStudent() {
        for(Enrollment enrollment : enrollments) {
            if(enrollment.isStudent()) {
                return true;
            }
        }
        return false;
    }

    public boolean isTeacher() {

        if (enrollments == null){
            return false;
        }

        for(Enrollment enrollment : enrollments) {
            if(enrollment.isTeacher()) {
                return true;
            }
        }
        return false;
    }

    public boolean isTA() {

        if (enrollments == null){
            return false;
        }

        for(Enrollment enrollment : enrollments) {
            if(enrollment.isTA()) {
                return true;
            }
        }
        return false;
    }

    public boolean isObserver()
    {
        for(Enrollment enrollment : enrollments) {
            if(enrollment.isObserver()) {
                return true;
            }
        }
        return false;
    }

    public Enrollment[] getEnrollmentsNoDuplicates() {
        if(enrollments == null) {
            return null;
        }
        if(enrollments.length <= 1) {
            return enrollments;
        }

        ArrayList<Enrollment> noDuplicates = new ArrayList<Enrollment>();
        for(Enrollment enrollment : enrollments) {
            if(!noDuplicates.contains(enrollment)) {
                noDuplicates.add(enrollment);
            }
        }
        return noDuplicates.toArray(new Enrollment[noDuplicates.size()]);
    }

    public double getCurrentScore() {
        if (currentScore == null) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.isStudent()) {
                    currentScore = enrollment.getCurrentScore();
                    return currentScore;
                }
            }
            currentScore = 0.0;
        }

        return currentScore;
    }

    public String getCurrentGrade() {
        if (!checkedCurrentGrade) {
            checkedCurrentGrade = true;
            for (Enrollment enrollment : enrollments) {
                if (enrollment.isStudent()) {
                    currentGrade = enrollment.getCurrentGrade();
                    return currentGrade;
                }
            }
        }

        return currentGrade;

    }
    public double getFinalScore() {
        if (finalScore == null) {
            for (Enrollment enrollment : enrollments) {
                if (enrollment.isStudent()) {
                    finalScore = enrollment.getFinalScore();
                    return finalScore;
                }
            }
            finalScore = 0.0;
        }

        return finalScore;
    }

    public String getFinalGrade() {
        if (!checkedFinalGrade) {
            checkedFinalGrade = true;
            for (Enrollment enrollment : enrollments) {
                if (enrollment.isStudent()) {
                    finalGrade = enrollment.getFinalGrade();
                }
            }
        }

        return finalGrade;
    }

    public void addEnrollment(Enrollment enrollment) {
        if (enrollments == null || enrollments.length == 0) {
            enrollments = new Enrollment[] { enrollment };
        } else {
            ArrayList<Enrollment> tempEnrollments = new ArrayList<Enrollment>(enrollments.length + 1);
            tempEnrollments.addAll(Arrays.asList(enrollments));
            tempEnrollments.add(enrollment);
            enrollments = tempEnrollments.toArray(new Enrollment[tempEnrollments.size()]);
        }
    }


    /**
     * License
     */
    public enum LICENSE {PRIVATE_COPYRIGHTED, CC_ATTRIBUTION_NON_COMMERCIAL_NO_DERIVATIVE, CC_ATTRIBUTION_NON_COMMERCIAL_SHARE_ALIKE,
        CC_ATTRIBUTION_NON_COMMERCIAL, CC_ATTRIBUTION_NO_DERIVATIVE, CC_ATTRIBUTION_SHARE_ALIKE, CC_ATTRIBUTION, PUBLIC_DOMAIN}

    public static String licenseToAPIString(LICENSE license){
        if(license == null){
            return null;
        }

        switch (license){
            case PRIVATE_COPYRIGHTED:
                return "private";
            case CC_ATTRIBUTION_NON_COMMERCIAL_NO_DERIVATIVE:
                return "cc_by_nc_nd";
            case CC_ATTRIBUTION_NON_COMMERCIAL_SHARE_ALIKE:
                return "c_by_nc_sa";
            case CC_ATTRIBUTION_NON_COMMERCIAL:
                return "cc_by_nc";
            case CC_ATTRIBUTION_NO_DERIVATIVE:
                return "cc_by_nd";
            case CC_ATTRIBUTION_SHARE_ALIKE:
                return "cc_by_sa";
            case CC_ATTRIBUTION:
                return "cc_by";
            case PUBLIC_DOMAIN:
                return "public_domain";
            default:
                return "";
        }
    }

    public static String licenseToPrettyPrint(LICENSE license){
        switch (license){
            case PRIVATE_COPYRIGHTED:
                return "Private (Copyrighted)";
            case CC_ATTRIBUTION_NON_COMMERCIAL_NO_DERIVATIVE:
                return "CC Attribution Non-Commercial No Derivatives";
            case CC_ATTRIBUTION_NON_COMMERCIAL_SHARE_ALIKE:
                return "CC Attribution Non-Commercial Share Alike";
            case CC_ATTRIBUTION_NON_COMMERCIAL:
                return "CC Attribution Non-Commercial";
            case CC_ATTRIBUTION_NO_DERIVATIVE:
                return "CC Attribution No Derivatives";
            case CC_ATTRIBUTION_SHARE_ALIKE:
                return "CC Attribution Share Alike";
            case CC_ATTRIBUTION:
                return "CC Attribution";
            case PUBLIC_DOMAIN:
                return "Public Domain";
            default:
                return "";
        }
    }


    public String getLicensePrettyPrint(){
        return licenseToPrettyPrint(getLicense());
    }


    public void setLicense(LICENSE license){
        this.license = licenseToAPIString(license);
    }


    public LICENSE getLicense(){

        if("public_domain".equals(license)){
            return LICENSE.PUBLIC_DOMAIN;
        } else if ("cc_by_nc_nd".equals(license)){
            return LICENSE.CC_ATTRIBUTION_NON_COMMERCIAL_NO_DERIVATIVE;
        } else if ("c_by_nc_sa".equals(license)){
            return LICENSE.CC_ATTRIBUTION_NON_COMMERCIAL_SHARE_ALIKE;
        } else if ("cc_by_nc".equals(license)){
            return LICENSE.CC_ATTRIBUTION_NON_COMMERCIAL;
        } else if ("cc_by_nd".equals(license)){
            return LICENSE.CC_ATTRIBUTION_NO_DERIVATIVE;
        } else if ("cc_by_sa".equals(license)){
            return LICENSE.CC_ATTRIBUTION_SHARE_ALIKE;
        } else if ("cc_by".equals(license)){
            return LICENSE.CC_ATTRIBUTION;
        } else {
            return LICENSE.PRIVATE_COPYRIGHTED;
        }
    }

<<<<<<< Updated upstream
=======
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeString(this.course_code);
        dest.writeString(this.start_at);
        dest.writeString(this.end_at);
        dest.writeString(this.syllabus_body);
        dest.writeByte(hide_final_grades ? (byte) 1 : (byte) 0);
        dest.writeByte(is_public ? (byte) 1 : (byte) 0);
        dest.writeString(this.license);
        dest.writeParcelable(this.term, flags);
        dest.writeParcelableArray(this.enrollments, flags);
        dest.writeValue(this.currentScore);
        dest.writeValue(this.finalScore);
        dest.writeByte(checkedCurrentGrade ? (byte) 1 : (byte) 0);
        dest.writeByte(checkedFinalGrade ? (byte) 1 : (byte) 0);
        dest.writeString(this.currentGrade);
        dest.writeString(this.finalGrade);
        dest.writeByte(isFavorite ? (byte) 1 : (byte) 0);
        dest.writeString(this.default_view);
        dest.writeSerializable(this.permissions);
    }

    private Course(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.course_code = in.readString();
        this.start_at = in.readString();
        this.end_at = in.readString();
        this.syllabus_body = in.readString();
        this.hide_final_grades = in.readByte() != 0;
        this.is_public = in.readByte() != 0;
        this.license = in.readString();
        this.term = in.readParcelable(Term.class.getClassLoader());
        this.enrollments = (Enrollment[])in.readParcelableArray(Enrollment.class.getClassLoader());
        this.currentScore = (Double) in.readValue(Double.class.getClassLoader());
        this.finalScore = (Double) in.readValue(Double.class.getClassLoader());
        this.checkedCurrentGrade = in.readByte() != 0;
        this.checkedFinalGrade = in.readByte() != 0;
        this.currentGrade = in.readString();
        this.finalGrade = in.readString();
        this.isFavorite = in.readByte() != 0;
        this.default_view = in.readString();
        this.permissions = (CanvasContextPermission) in.readSerializable();
    }

    public static Creator<Course> CREATOR = new Creator<Course>() {
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
>>>>>>> Stashed changes
}
