package com.instructure.canvasapi.model;

/**
 * Created by brady on 5/27/14.
 */
public class PollSubmissionResponse {

    private PollSubmission[] poll_submissions;

    public PollSubmission[] getPoll_submissions() {
        return poll_submissions;
    }

    public void setPoll_submissions(PollSubmission[] poll_submissions) {
        this.poll_submissions = poll_submissions;
    }
}
