package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.DiscussionTopicHeader;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class AnnouncementAPI {

    private static String getFirstPageAnnouncementsCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() +"/announcements";
    }

    interface AnnouncementsInterface {
        @GET("/{context_id}/discussion_topics?only_announcements=1")
        void getFirstPageAnnouncementsList(@Path("context_id") long context_id, Callback<DiscussionTopicHeader[]> callback);

        @GET("/{next}")
        void getNextPageAnnouncementsList(@EncodedPath("next") String nextURL, Callback<DiscussionTopicHeader[]> callback);
    }
    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AnnouncementsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(AnnouncementsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageAnnouncements(CanvasContext canvasContext, CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageAnnouncementsCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPageAnnouncementsList(canvasContext.getId(), callback);
    }

    public static void getNextPageAnnouncements(String nextURL, CanvasCallback<DiscussionTopicHeader[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageAnnouncementsList(nextURL, callback);
    }
}
