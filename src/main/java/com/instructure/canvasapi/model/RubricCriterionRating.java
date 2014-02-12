package com.instructure.canvasapi.model;

import java.io.Serializable;


/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class RubricCriterionRating implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
    private String criterionId;
	private String description;
	private double points;
	private String comments;
    private boolean isGrade;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getCriterionId() {
        return criterionId;
    }
    public void setCriterionId(String criterionId) {
        this.criterionId = criterionId;
    }
    public String getRatingDescription() {
		return description;
	}
	public void setRatingDescription(String ratingDescription) {
		this.description = ratingDescription;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
    public boolean isGrade() {
        return isGrade;
    }
    public void setGrade(boolean grade) {
        isGrade = grade;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RubricCriterionRating rating = (RubricCriterionRating) o;

        if (criterionId != null ? !criterionId.equals(rating.criterionId) : rating.criterionId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return criterionId != null ? criterionId.hashCode() : 0;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public RubricCriterionRating() {}

    public RubricCriterionRating(String criterionId) {
        setGrade(false);
        setCriterionId(criterionId);
    }

    public RubricCriterionRating(RubricCriterion rubricCriterion) {
        setGrade(false);
        setCriterionId(rubricCriterion.getId());
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    public boolean isComment() {
        return getComments() != null && !getComments().equals("");
    }
}
