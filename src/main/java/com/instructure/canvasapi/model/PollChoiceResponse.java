package com.instructure.canvasapi.model;

/**
 * Created by brady on 5/27/14.
 */
public class PollChoiceResponse {

    PollChoice[] poll_choices;

    public PollChoice[] getPoll_choices() {
        return poll_choices;
    }

    public void setPoll_choices(PollChoice[] poll_choices) {
        this.poll_choices = poll_choices;
    }
}
