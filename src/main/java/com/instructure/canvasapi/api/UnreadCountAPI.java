package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.UnreadConversationCount;
import com.instructure.canvasapi.model.UnreadNotificationCount;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UnreadCountAPI {

    private static String getUnreadConversationCountCacheFilename(){
        return "/conversations/unread_count";
    }
    private static String getNotificationsCountCacheFilename(){
        return "/users/self/activity_stream/summary";
    }

    interface UnreadCountsInterface {
        @GET("/conversations/unread_count")
        void getUnreadConversationCount(Callback<UnreadConversationCount> callback);

        @GET("/users/self/activity_stream/summary")
        void getNotificationsCount(Callback<UnreadNotificationCount[]> callback);
    }

    private static UnreadCountsInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(UnreadCountsInterface.class);
    }

    public static void getUnreadConversationCount(CanvasCallback<UnreadConversationCount> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getUnreadConversationCountCacheFilename());
        buildInterface(callback).getUnreadConversationCount(callback);
    }

    public static void getUnreadNotificationsCount(CanvasCallback<UnreadNotificationCount[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getNotificationsCountCacheFilename());
        buildInterface(callback).getNotificationsCount(callback);
    }
}
