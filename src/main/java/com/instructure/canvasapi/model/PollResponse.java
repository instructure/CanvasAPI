package com.instructure.canvasapi.model;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class PollResponse {

    private Poll[] polls;

    public Poll[] getPolls() {
        return polls;
    }

    public void setPolls(Poll[] polls) {
        this.polls = polls;
    }
}
