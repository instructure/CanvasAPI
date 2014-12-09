package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.DiscussionEntry;
import com.instructure.canvasapi.model.DiscussionTopic;
import com.instructure.canvasapi.model.DiscussionTopicHeader;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.*;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DiscussionAPI {

    private static String getFirstPageDiscussionsCacheFilename(CanvasContext canvasContext) {
        return canvasContext.toAPIString() + "/discussion_topics";
    }

    private static String getDetailedDiscussionsCacheFilename(CanvasContext canvasContext, long discussionID) {
        return canvasContext.toAPIString() + "/discussion_topics/" + discussionID;
    }

    private static String getFullDiscussionsCacheFilename(CanvasContext canvasContext, long discussionID) {
        return canvasContext.toAPIString() + "/discussion_topics/" + discussionID + "/view";
    }

    interface DiscussionsInterface {
        @GET("/{context_id}/discussion_topics")
        void getFirstPageDiscussions(@Path("context_id") long course_id, Callback<DiscussionTopicHeader[]> callback);

        @GET("/{next}")
        void getNextPageDiscussions(@Path(value = "next", encode = false) String nextURL, Callback<DiscussionTopicHeader[]> callback);

        @GET("/{context_id}/discussion_topics/{discussionid}")
        void getDetailedDiscussion(@Path("context_id") long courseId, @Path("discussionid") long discussionId, Callback<DiscussionTopicHeader> callback);

        @GET("/{context_id}/discussion_topics/{discussionid}/view")
        void getFullDiscussionTopic(@Path("context_id") long courseId, @Path("discussionid") long discussionId, Callback<DiscussionTopic> callback);

        @GET("/{context_id}/discussion_topics/")
        void getFilteredDiscussionTopic(@Path("context_id") long courseId, @Query("search_term") String searchTerm, Callback<DiscussionTopicHeader[]> callback);

        @POST("/{context_id}/discussion_topics/{discussionid}/entries/")
        void postDiscussionEntry(@Path("context_id") long courseId, @Path("discussionid") long discussionId, @Query("message") String message, Callback<DiscussionEntry> callback);

        @POST("/{context_id}/discussion_topics/{discussionid}/entries/{entryid}/replies")
        void postDiscussionReply(@Path("context_id") long courseId, @Path("discussionid") long discussionId, @Path("entryid") long entryId, @Query("message")String message, Callback<DiscussionEntry> callback);

        @POST("/{context_id}/discussion_topics/")
        void postNewDiscussion(@Path("context_id")long courseId, @Query("title") String title, @Query("message")String message, @Query("is_announcement")int announcement, @Query("discussion_type")String discussion_type, Callback<DiscussionTopicHeader> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static DiscussionsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return restAdapter.create(DiscussionsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageDiscussions(CanvasContext canvasContext, final CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageDiscussionsCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPageDiscussions(canvasContext.getId(), callback);
    }

    public static void getNextPageDiscussions(String nextURL, CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageDiscussions(nextURL, callback);
    }

    public static void getDetailedDiscussion(CanvasContext canvasContext, long discussion_id, CanvasCallback<DiscussionTopicHeader> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getDetailedDiscussionsCacheFilename(canvasContext, discussion_id));
        buildInterface(callback, canvasContext).getDetailedDiscussion(canvasContext.getId(), discussion_id, callback);
    }

    public static void getFullDiscussionTopic(CanvasContext canvasContext, long discussion_id, CanvasCallback<DiscussionTopic> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFullDiscussionsCacheFilename(canvasContext, discussion_id));
        buildInterface(callback, canvasContext).getFullDiscussionTopic(canvasContext.getId(), discussion_id, callback);
    }

    public static void getFilteredDiscussionTopic(CanvasContext canvasContext, String searchTerm,  CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(callback, canvasContext).getFilteredDiscussionTopic(canvasContext.getId(), searchTerm, callback);
    }

    public static void postDiscussionEntry(CanvasContext canvasContext, long discussionId, String message, CanvasCallback<DiscussionEntry> callback){
        if (APIHelpers.paramIsNull(callback, message, canvasContext)) { return; }

        buildInterface(callback, canvasContext).postDiscussionEntry(canvasContext.getId(), discussionId, message, callback);
    }

    public static void postDiscussionReply(CanvasContext canvasContext, long discussionId, long entryId, String message, CanvasCallback<DiscussionEntry> callback){
        if (APIHelpers.paramIsNull(callback, message, canvasContext)) { return; }

        buildInterface(callback, canvasContext).postDiscussionReply(canvasContext.getId(), discussionId, entryId, message, callback);
    }

    public static void postNewDiscussion(CanvasContext canvasContext, String  title, String message, boolean threaded, boolean is_announcement, CanvasCallback<DiscussionTopicHeader> callback){
        if (APIHelpers.paramIsNull(callback, message, title, canvasContext)) { return; }


        String type = "";
        if (threaded)
            type = "threaded";
        else
            type = "side_comment";

        int announcement = APIHelpers.booleanToInt(is_announcement);

        buildInterface(callback, canvasContext).postNewDiscussion(canvasContext.getId(), title, message, announcement, type, callback);
    }

}
