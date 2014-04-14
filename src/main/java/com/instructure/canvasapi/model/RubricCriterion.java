package com.instructure.canvasapi.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class RubricCriterion implements Serializable , Comparable<RubricCriterion> {

	private static final long serialVersionUID = 1L;
	
	private String id;
	private Rubric rubric;
	private String description;
	private String long_description;
	private double points;
	private List<RubricCriterionRating> ratings = new ArrayList<RubricCriterionRating>();

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Rubric getRubric() {
		return rubric;
	}
	public void setRubric(Rubric rubric) {
		this.rubric = rubric;
	}
	public String getCriterionDescription() {
		return description;
	}
	public void setCriterionDescription(String criterionDescription) {
		this.description = criterionDescription;
	}
	public String getLongDescription() {
		return long_description;
	}
	public void setLongDescription(String longDescription) {
		this.long_description = longDescription;
	}
	public double getPoints() {
		return points;
	}
	public void setPoints(double points) {
		this.points = points;
	}

	public List<RubricCriterionRating> getRatings() {
		return ratings;
	}
	public void setRatings(List<RubricCriterionRating> ratings) {
		this.ratings = ratings;
	}

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public RubricCriterion(Rubric rubric) {
        setRubric(rubric);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RubricCriterion that = (RubricCriterion) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public void markGrade(RubricCriterionRating rating) {
        for (RubricCriterionRating criterionRating : ratings) {
            if (criterionRating.getPoints() == rating.getPoints()) {
                criterionRating.setGrade(true);
            }
        }

        if (rating.isComment() && !ratings.contains(rating)) {
            rating.setRatingDescription(rating.getComments());
            ratings.add(rating);
        }

    }



    public void markGrades(RubricAssessment rubricAssessment, RubricCriterion[] criteria) {
        if (rubricAssessment == null) {
            return;
        }

        for (RubricCriterionRating rating : rubricAssessment.getRatings()) {
            for (RubricCriterion criterion : criteria) {
                if (criterion.getId().equals(rating.getCriterionId())) {
                    criterion.markGrade(rating);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int compareTo(RubricCriterion rubricCriterion) {
        return this.getId().compareTo(rubricCriterion.getId());
    }
}
