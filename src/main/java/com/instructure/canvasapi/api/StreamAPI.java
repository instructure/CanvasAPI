package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.HiddenStreamItem;
import com.instructure.canvasapi.model.StreamItem;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Joshua Dutton on 10/22/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class StreamAPI {
    private static String getUserStreamCacheFilename(){
        return "/users/self/activity_stream";
    }

    private static String getCourseStreamCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/activity_stream";
    }

    interface StreamInterface {
        @GET("/users/self/activity_stream")
        void getUserStream(Callback<StreamItem[]> callback);

        @DELETE("/users/self/activity_stream/{streamID}")
        void hideStreamItem(@Path("streamID")long streamID, Callback<HiddenStreamItem> callback);

        @GET("/{context_id}/activity_stream")
        void getContextStream(@Path("context_id") long context_id, Callback<StreamItem[]> callback);

        @GET("/{next}")
        void getNextPageStream(@EncodedPath("next") String nextURL, Callback<StreamItem[]> callback);
    }


    private static StreamInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback.getContext(), canvasContext);
        return restAdapter.create(StreamInterface.class);
    }


    public static void getFirstPageUserStream(CanvasCallback<StreamItem[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getUserStreamCacheFilename());
        buildInterface(callback, null).getUserStream(callback);
    }

    public static void getFirstPageCourseStream(CanvasContext canvasContext, CanvasCallback<StreamItem[]> callback) {
        if (APIHelpers.paramIsNull(callback ,canvasContext)) { return; }

        callback.readFromCache(getCourseStreamCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getContextStream(canvasContext.getId(), callback);
    }

    public static void getNextPageStream(String nextUrl, CanvasCallback<StreamItem[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextUrl)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageStream(nextUrl, callback);
    }

    public static void hideStreamItem(long streamId, final CanvasCallback<HiddenStreamItem> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }
        buildInterface(callback, null).hideStreamItem(streamId,callback);
    }
}
