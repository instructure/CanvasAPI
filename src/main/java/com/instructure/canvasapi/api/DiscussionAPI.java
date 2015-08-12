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
import retrofit.client.Response;
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

    private static String getFirstPagePinnedDiscussionsCacheFilename(CanvasContext canvasContext) {
        return canvasContext.toAPIString() + "/discussion_topics?scope=pinned";
    }

    private static String getStudentGroupDiscussionTopicHeaderFilename(CanvasContext canvasContext, long rootTopicId) {
        return canvasContext.toAPIString() + "/discussion_topics?root_topic_id=" + rootTopicId;
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

        @GET("/{context_id}/discussion_topics?scope=pinned")
        void getFirstPagePinnedDiscussions(@Path("context_id") long course_id, Callback<DiscussionTopicHeader[]> callback);

        @GET("/{context_id}/discussion_topics")
        void getStudentGroupDiscussionTopicHeader(@Path("context_id") long course_id, @Query("root_topic_id") long rootTopicId, Callback<DiscussionTopicHeader[]> callback);

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

        @POST("/{context_id}/discussion_topics/")
        void postNewDiscussionAndPublish(@Path("context_id")long courseId, @Query("title") String title, @Query("message")String message, @Query("is_announcement")int announcement, @Query("published") int isPublished, @Query("discussion_type")String discussion_type, Callback<DiscussionTopicHeader> callback);

        @PUT("/{context_id}/discussion_topics/{topic_id}")
        void updateDiscussionTopic(@Path("context_id") long courseId, @Path("topic_id") long topicId, @Query("title") String title, @Query("message")String message, @Query("published") int isPublished, @Query("discussion_type")String discussion_type, Callback<DiscussionTopicHeader> callback);

        @POST("/{context_id}/discussion_topics/{discussionId}/entries/{entryId}/rating")
        void rateDiscussionEntry(@Path("context_id") long courseId, @Path("discussionId") long discussionId, @Path("entryId") long entryId, @Query("rating") int rating, Callback<Response> callback);

        @PUT("/{context_id}/discussion_topics/{topic_id}")
        void pinDiscussion(@Path("context_id") long courseId, @Path("topic_id") long discussionId, @Query("pinned") boolean pinned, Callback<DiscussionTopicHeader> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static DiscussionsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
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

    public static void getFirstPagePinnedDiscussions(CanvasContext canvasContext, final CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPagePinnedDiscussionsCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPagePinnedDiscussions(canvasContext.getId(), callback);
    }

    public static void getStudentGroupDiscussionTopicHeader(CanvasContext canvasContext, long rootTopicId, final CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getStudentGroupDiscussionTopicHeaderFilename(canvasContext, rootTopicId));
        buildInterface(callback, canvasContext).getStudentGroupDiscussionTopicHeader(canvasContext.getId(), rootTopicId, callback);
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

    public static void postNewDiscussionAndPublish(CanvasContext canvasContext, String  title, String message, boolean threaded, boolean is_announcement, boolean isPublished, CanvasCallback<DiscussionTopicHeader> callback){
        if (APIHelpers.paramIsNull(callback, message, title, canvasContext)) { return; }


        String type = "";
        if (threaded)
            type = "threaded";
        else
            type = "side_comment";

        int announcement = APIHelpers.booleanToInt(is_announcement);
        int publish = APIHelpers.booleanToInt(isPublished);

        buildInterface(callback, canvasContext).postNewDiscussionAndPublish(canvasContext.getId(), title, message, announcement, publish, type, callback);
    }

    public static void updateDiscussionTopic(CanvasContext canvasContext, long topicId, String  title, String message, boolean threaded, boolean isPublished, CanvasCallback<DiscussionTopicHeader> callback){
        if (APIHelpers.paramIsNull(callback, message, title, canvasContext)) { return; }

        String type = "";
        if (threaded)
            type = "threaded";
        else
            type = "side_comment";

        int publish = APIHelpers.booleanToInt(isPublished);

        buildInterface(callback, canvasContext).updateDiscussionTopic(canvasContext.getId(), topicId, title, message, publish, type, callback);

    }

    /**
     * @param rating can only be 1 or 0
     */
    public static void rateDiscussionEntry(CanvasContext canvasContext, long discussionId, long entryId, int rating, CanvasCallback<Response> callback){
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(callback, canvasContext).rateDiscussionEntry(canvasContext.getId(), discussionId, entryId, rating, callback);
    }

    /**
     * Allows users with correct permissions to pin a discussion
     * @param canvasContext A CanvasContext
     * @param topicId The topic id of the DiscussionTopicHeader (not the root topic id)
     * @param pin A true/false value to denote if you wish to pin or unpin a discussion
     * @param callback A java thing that is kind of stinky
     */
    public static void pinDiscussion(CanvasContext canvasContext, long topicId, boolean pin, CanvasCallback<DiscussionTopicHeader> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(callback, canvasContext).pinDiscussion(canvasContext.getId(), topicId, pin, callback);
    }
}
