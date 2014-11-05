package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.PollSessionResponse;
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
 * Created by brady on 5/20/14.
 */
public class PollSessionAPI {

    private static String getFirstPagePollSessionsCacheFilename(long poll_id){
        return "api/v1/polls/" + poll_id + "/poll_sessions";
    }

    private static String getSinglePollSessionsCacheFilename(long poll_id, long poll_session_id){
        return "api/v1/polls/" + poll_id + "/poll_sessions/" + poll_session_id;
    }

    interface PollSessionInterface {
        @GET("/polls/{pollid}/poll_sessions")
        void getFirstPagePollSessionsList(@Path("pollid") long poll_id, Callback<PollSessionResponse> callback);

        @GET("/{next}")
        void getNextPagePollSessionsList(@Path("next") String nextURL, Callback<PollSessionResponse> callback);

        @GET("/polls/{pollid}/poll_sessions/{poll_session_id}")
        void getSinglePollSession(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, Callback<PollSessionResponse> callback);

        @POST("/polls/{pollid}/poll_sessions")
        void createPollSession(@Path("pollid") long poll_id, @Query("poll_sessions[][course_id]") long course_id, @Query("poll_sessions[][course_section_id]") long course_section_id, Callback<PollSessionResponse> callback);

        @PUT("/polls/{pollid}/poll_sessions/{poll_session_id}")
        void updatePollSession(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id,  @Query("poll_sessions[][course_id]") long course_id, @Query("poll_sessions[][course_section_id]") long course_section_id, @Query("poll_sessions[][has_public_results]") boolean has_public_results, Callback<PollSessionResponse> callback);

        @DELETE("/polls/{pollid}/poll_sessions/{poll_session_id}")
        void deletePollSession(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, Callback<Response> callback);

        @GET("/polls/{pollid}/poll_sessions/{poll_session_id}/open")
        void openPollSession(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, Callback<Response> callback);

        @GET("/polls/{pollid}/poll_sessions/{poll_session_id}/close")
        void closePollSession(@Path("pollid") long poll_id, @Path("poll_session_id") long poll_session_id, Callback<Response> callback);

        @GET("/poll_sessions/opened")
        void getOpenSessions(Callback<PollSessionResponse> callback);

        @GET("/poll_sessions/closed")
        void getClosedSessions(Callback<PollSessionResponse> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static PollSessionInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(PollSessionInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPagePollSessions(long poll_id, CanvasCallback<PollSessionResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getFirstPagePollSessionsCacheFilename(poll_id));
        buildInterface(callback).getFirstPagePollSessionsList(poll_id, callback);
    }

    public static void getNextPagePollSessions(String nextURL, CanvasCallback<PollSessionResponse> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPagePollSessionsList(nextURL, callback);
    }

    public static void getSinglePollSession(long poll_id, long poll_session_id, CanvasCallback<PollSessionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id)) { return; }

        callback.readFromCache(getSinglePollSessionsCacheFilename(poll_id, poll_session_id));
        buildInterface(callback).getSinglePollSession(poll_id, poll_session_id, callback);
    }

    public static void createPollSession(long poll_id, long course_id, long course_section_id, CanvasCallback<PollSessionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, course_id, course_section_id)) { return; }

        buildInterface(callback).createPollSession(poll_id, course_id, course_section_id, callback);
    }

    public static void updatePollSession(long poll_id, long poll_session_id, long course_id, long course_section_id, boolean has_public_results, CanvasCallback<PollSessionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id, course_id, course_section_id, has_public_results)) { return; }

        buildInterface(callback).updatePollSession(poll_id, poll_session_id, course_id, course_section_id, has_public_results, callback);
    }

    public static void deletePollSession(long poll_id, long poll_session_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id)) { return; }

        buildInterface(callback).deletePollSession(poll_id, poll_session_id, callback);
    }

    public static void openPollSession(long poll_id, long poll_session_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id)) { return; }

        buildInterface(callback).openPollSession(poll_id, poll_session_id, callback);
    }

    public static void closePollSession(long poll_id, long poll_session_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, poll_id, poll_session_id)) { return; }

        buildInterface(callback).closePollSession(poll_id, poll_session_id, callback);
    }

    public static void getOpenSessions(CanvasCallback<PollSessionResponse> callback) {
        buildInterface(callback).getOpenSessions(callback);
    }

    public static void getClosedSessions(CanvasCallback<PollSessionResponse> callback) {
        buildInterface(callback).getClosedSessions(callback);
    }
}
