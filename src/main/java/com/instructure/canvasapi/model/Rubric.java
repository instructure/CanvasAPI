package com.instructure.canvasapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Rubric implements Serializable {

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

}
