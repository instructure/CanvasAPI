package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.DiscussionTopicHeader;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Path;
import retrofit.http.GET;

/**
 *
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class AnnouncementAPI extends BuildInterfaceAPI {

    interface AnnouncementsInterface {
        @GET("/{context_id}/discussion_topics?only_announcements=1")
        void getFirstPageAnnouncementsList(@Path("context_id") long context_id, Callback<DiscussionTopicHeader[]> callback);

        @GET("/{next}")
        void getNextPageAnnouncementsList(@Path(value = "next", encode = false) String nextURL, Callback<DiscussionTopicHeader[]> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageAnnouncements(CanvasContext canvasContext, CanvasCallback<DiscussionTopicHeader[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(AnnouncementsInterface.class, callback, canvasContext).getFirstPageAnnouncementsList(canvasContext.getId(), callback);
        buildInterface(AnnouncementsInterface.class, callback, canvasContext).getFirstPageAnnouncementsList(canvasContext.getId(), callback);
    }

    public static void getNextPageAnnouncements(String nextURL, CanvasCallback<DiscussionTopicHeader[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildCacheInterface(AnnouncementsInterface.class, callback, false).getNextPageAnnouncementsList(nextURL, callback);
        buildInterface(AnnouncementsInterface.class, callback, false).getNextPageAnnouncementsList(nextURL, callback);
    }
}
