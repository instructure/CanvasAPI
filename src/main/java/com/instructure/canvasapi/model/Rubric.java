package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Rubric implements Parcelable {

	private static final long serialVersionUID = 1L;
	
	private Assignment assignment;
	private List<RubricCriterion> criteria = new ArrayList<RubricCriterion>();
	private boolean free_form_criterion_comments;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
	
	public Assignment getAssignment() {
		return assignment;
	}
	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	public List<RubricCriterion> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<RubricCriterion> criteria) {
		this.criteria = criteria;
	}
	public boolean isFreeFormComments() {
		return free_form_criterion_comments;
	}
	public void setFreeFormComments(boolean freeFormComments) {
		this.free_form_criterion_comments = freeFormComments;
	}

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Rubric(Assignment assignment) {
        setAssignment(assignment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.assignment, flags);
        dest.writeTypedList(criteria);
        dest.writeByte(free_form_criterion_comments ? (byte) 1 : (byte) 0);
    }

    private Rubric(Parcel in) {
        in.readTypedList(criteria, RubricCriterion.CREATOR);
        this.free_form_criterion_comments = in.readByte() != 0;
    }

    public static Creator<Rubric> CREATOR = new Creator<Rubric>() {
        public Rubric createFromParcel(Parcel source) {
            return new Rubric(source);
        }

        public Rubric[] newArray(int size) {
            return new Rubric[size];
        }
    };
}
