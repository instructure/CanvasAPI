package com.instructure.canvasapi.model;

/**
 * Created by brady on 5/27/14.
 */
public class PollSessionResponse {

    private PollSession[] poll_sessions;

    public PollSession[] getPollSessions() {
        return poll_sessions;
    }

    public void setPollSessions(PollSession[] pollSessions) {
        this.poll_sessions = pollSessions;
    }
}
