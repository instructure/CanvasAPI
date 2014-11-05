package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ModuleItem;
import com.instructure.canvasapi.model.ModuleObject;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Path;
import retrofit.http.GET;

/**
 * Created by Brady Larson on 10/15/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ModuleAPI {

    private static String getFirstPageModuleObjectsCacheFilename(CanvasContext canvasContext) {
        return canvasContext.toAPIString() + "/modules";
    }

    private static String getFirstPageModuleItemsCacheFilename(CanvasContext canvasContext, long moduleID) {
        return canvasContext.toAPIString() + "/modules/" + moduleID + "/items";
    }

    interface ModulesInterface {
        @GET("/{context_id}/modules")
        void getFirstPageModuleObjects(@Path("context_id") long context_id, Callback<ModuleObject[]> callback);

        @GET("/{next}")
        void getNextPageModuleObjectList(@Path("next") String nextURL, Callback<ModuleObject[]> callback);

        @GET("/{context_id}/modules/{moduleid}/items")
        void getFirstPageModuleItems(@Path("context_id") long context_id, @Path("moduleid") long moduleID, Callback<ModuleItem[]> callback);

        @GET("/{next}")
        void getNextPageModuleItemList(@Path("next") String nextURL, Callback<ModuleItem[]> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static ModulesInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(ModulesInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageModuleObjects(CanvasContext canvasContext, CanvasCallback<ModuleObject[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageModuleObjectsCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPageModuleObjects(canvasContext.getId(), callback);
    }

    public static void getNextPageModuleObjects(String nextURL, CanvasCallback<ModuleObject[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageModuleObjectList(nextURL, callback);
    }

    public static void getFirstPageModuleItems(CanvasContext canvasContext, long moduleId, CanvasCallback<ModuleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getFirstPageModuleItemsCacheFilename(canvasContext, moduleId));
        buildInterface(callback, canvasContext).getFirstPageModuleItems(canvasContext.getId(), moduleId, callback);
    }

    public static void getNextPageModuleItems(String nextURL, CanvasCallback<ModuleItem[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageModuleItemList(nextURL, callback);
    }
}
