package com.instructure.canvasapi.api;

import android.content.Context;
import android.util.Log;

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

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/conversations/unread_count")
        UnreadConversationCount getUnreadConversationCountSynchronous();
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static UnreadCountsInterface buildInterface(CanvasCallback<?> callback) {

        return buildInterface(callback.getContext());
    }
    private static UnreadCountsInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(UnreadCountsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

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

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////

    public static String getUnreadConversationsCountSynchronous(Context context){

        try{
            return  buildInterface(context).getUnreadConversationCountSynchronous().getUnreadCount();
        } catch (Exception E){
            return null;
        }
    }
}
