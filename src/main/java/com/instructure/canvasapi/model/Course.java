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
}
