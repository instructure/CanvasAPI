package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.PollChoiceResponse;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Path;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by Brady Larson on 5/20/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class PollChoiceAPI {

    private static String getFirstPagePollChoicesCacheFilename(long poll_id){
        return "api/v1/polls/" + poll_id + "/poll_choices";
    }

    private static String getSinglePollChoiceCacheFilename(long poll_id, long poll_choice_id){
        return "api/v1/polls/" + poll_id + "/poll_choices/" + poll_choice_id;
    }

    interface PollChoiceInterface {
        @GET("/polls/{pollid}/poll_choices")
        void getFirstPagePollChoicesList(@Path("pollid") long poll_id, Callback<PollChoiceResponse> callback);

        @GET("/{next}")
        void getNextPagePollChoicesList(@Path(value = "next", encode = false) String nextURL, Callback<PollChoiceResponse> callback);

        @GET("/polls/{pollid}/poll_choices/{poll_choice_id}")
        void getSinglePollChoice(@Path("pollid") long poll_id, @Path("poll_choice_id") long poll_choice_id, Callback<PollChoiceResponse> callback);

        @POST("/polls/{pollid}/poll_choices")
        void createPollChoice(@Path("pollid") long poll_id, @Query("poll_choices[][text]") String pollChoiceText, @Query("poll_choices[][is_correct]") boolean isCorrect, @Query("poll_choices[][position]") int position, Callback<PollChoiceResponse> callback);

        @PUT("/polls/{pollid}/poll_choices/{poll_choice_id}")
        void updatePollChoice(@Path("pollid") long poll_id, @Path("poll_choice_id") long poll_choice_id, @Query("poll_choices[][text]") String pollChoiceText, @Query("poll_choices[][is_correct]") boolean isCorrect, @Query("poll_choices[][position]") int position, Callback<PollChoiceResponse> callback);

        @DELETE("/polls/{pollid}/poll_choices/{poll_choice_id}")
        void deletePollChoice(@Path("pollid") long poll_id, @Path("poll_choice_id") long poll_choice_id, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static PollChoiceInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(PollChoiceInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPagePollChoices(long poll_id, CanvasCallback<PollChoiceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getFirstPagePollChoicesCacheFilename(poll_id));
        buildInterface(callback).getFirstPagePollChoicesList(poll_id, callback);
    }

    public static void getNextPagePollChoices(String nextURL, CanvasCallback<PollChoiceResponse> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPagePollChoicesList(nextURL, callback);
    }

    public static void getSinglePollChoice(long poll_id, long poll_choice_id, CanvasCallback<PollChoiceResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_choice_id)) { return; }

        callback.readFromCache(getSinglePollChoiceCacheFilename(poll_id, poll_choice_id));
        buildInterface(callback).getSinglePollChoice(poll_id, poll_choice_id, callback);
    }

    public static void createPollChoice(long poll_id, String text, boolean is_correct, int position, CanvasCallback<PollChoiceResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, text, is_correct)) { return; }

        buildInterface(callback).createPollChoice(poll_id, text, is_correct, position, callback);
    }

    public static void updatePollChoice(long poll_id, long poll_choice_id, String text, boolean is_correct, int position, CanvasCallback<PollChoiceResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_choice_id, text, is_correct)) { return; }

        buildInterface(callback).updatePollChoice(poll_id, poll_choice_id, text, is_correct, position, callback);
    }

    public static void deletePollChoice(long poll_id, long poll_choice_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_choice_id)) { return; }

        buildInterface(callback).deletePollChoice(poll_id, poll_choice_id, callback);
    }
}
