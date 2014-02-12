package com.instructure.canvasapi.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class RubricAssessment implements Serializable {
    private static final long serialVersionUID = 1L;

    private ArrayList<RubricCriterionRating> ratings = new ArrayList<RubricCriterionRating>();

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public ArrayList<RubricCriterionRating> getRatings() {
        return ratings;
    }
    public void setRatings(ArrayList<RubricCriterionRating> ratings) {
        this.ratings = ratings;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public RubricAssessment() {}

}
