package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.PollResponse;
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
public class PollAPI {

    private static String getFirstPagePollsCacheFilename(){
        return "api/v1/polls";
    }

    private static String getSinglePollCacheFilename(long poll_id){
        return "api/v1/polls/" + poll_id;
    }

    interface PollInterface {
        @GET("/polls")
        void getFirstPagePollsList(Callback<PollResponse> callback);

        @GET("/{next}")
        void getNextPagePollsList(@Path("next") String nextURL, Callback<PollResponse> callback);

        @GET("/polls/{pollid}")
        void getSinglePoll(@Path("pollid") long poll_id, Callback<PollResponse> callback);

        @POST("/polls")
        void createPoll(@Query("polls[][question]") String pollTitle, Callback<PollResponse> callback);

        @PUT("/polls/{pollid}")
        void updatePoll(@Path("pollid") long poll_id, @Query("polls[][question]") String pollTitle, Callback<PollResponse> callback);

        @DELETE("/polls/{pollid}")
        void deletePoll(@Path("pollid") long poll_id, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static PollInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(PollInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPagePoll(CanvasCallback<PollResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getFirstPagePollsCacheFilename());
        buildInterface(callback).getFirstPagePollsList(callback);
    }

    public static void getNextPagePoll(String nextURL, CanvasCallback<PollResponse> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPagePollsList(nextURL, callback);
    }

    public static void getSinglePoll(long poll_id, CanvasCallback<PollResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id)) { return; }

        callback.readFromCache(getSinglePollCacheFilename(poll_id));
        buildInterface(callback).getSinglePoll(poll_id, callback);
    }

    public static void createPoll(String title, CanvasCallback<PollResponse> callback) {
        if (APIHelpers.paramIsNull(callback, title)) { return; }

        buildInterface(callback).createPoll(title, callback);
    }

    public static void updatePoll(long poll_id, String title, CanvasCallback<PollResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, title)) { return; }

        buildInterface(callback).updatePoll(poll_id, title, callback);
    }

    public static void deletePoll(long poll_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id)) { return; }

        buildInterface(callback).deletePoll(poll_id, callback);
    }
}
