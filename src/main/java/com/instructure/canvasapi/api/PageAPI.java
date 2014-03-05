package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Page;
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
public class PageAPI {

    private static String getFirstPagePagesCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/pages";
    }

    private static String getDetailedPageCacheFilename(CanvasContext canvasContext, String pageID){
        return canvasContext.toAPIString() + "/pages/"+pageID;
    }

    interface PagesInterface {
        @GET("/{context_id}/pages")
        void getFirstPagePagesList(@Path("context_id") long context_id, Callback<Page[]> callback);

        @GET("/{next}")
        void getNextPagePagesList(@EncodedPath("next") String nextURL, Callback<Page[]> callback);

        @GET("/{context_id}/pages/{pageid}")
        void getDetailedPage(@Path("context_id") long context_id, @Path("pageid") String page_id, Callback<Page> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static PagesInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(PagesInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPagePages(CanvasContext canvasContext, CanvasCallback<Page[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPagePagesCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPagePagesList(canvasContext.getId(), callback);
    }

    public static void getNextPagePages(String nextURL, CanvasCallback<Page[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPagePagesList(nextURL, callback);
    }

    public static void getDetailedPage(CanvasContext canvasContext, String page_id, CanvasCallback<Page> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getDetailedPageCacheFilename(canvasContext,page_id));
        buildInterface(callback, canvasContext).getDetailedPage(canvasContext.getId(), page_id, callback);
    }
}
