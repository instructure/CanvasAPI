package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.PollSubmissionResponse;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Brady Larson on 5/20/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class PollSubmissionAPI {

    private static String getPollSubmissionCacheFilename(long poll_id, long poll_session_id, long poll_submission_id) {
        return "/api/v1/polls/" + poll_id + "/poll_sessions/" + poll_session_id + "/poll_submissions/" + poll_submission_id;
    }
    interface PollSubmissionInterface {
        @GET("/polls/{pollid}/poll_sessions/{poll_session_id}/poll_submissions/{poll_submission_id}")
        void getPollSubmission(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, @Path("poll_submission_id") long poll_submission_id, Callback<PollSubmissionResponse> callback);

        @POST("/polls/{pollid}/poll_sessions/{poll_session_id}/poll_submissions/")
        void createPollSubmission(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, @Query("poll_submissions[][poll_choice_id]") long poll_choice_id, Callback<PollSubmissionResponse> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static PollSubmissionInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(PollSubmissionInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getPollSubmission(long poll_id, long poll_session_id, long poll_submission_id, CanvasCallback<PollSubmissionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id, poll_submission_id)) { return; }

        callback.readFromCache(getPollSubmissionCacheFilename(poll_id, poll_session_id, poll_submission_id));
        buildInterface(callback).getPollSubmission(poll_id, poll_session_id, poll_submission_id, callback);
    }

    public static void createPollSubmission(long poll_id, long poll_session_id, long poll_choice_id, CanvasCallback<PollSubmissionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id, poll_choice_id)) { return; }

        buildInterface(callback).createPollSubmission(poll_id, poll_session_id, poll_choice_id, callback);
    }

}
