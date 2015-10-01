package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ModuleItem;
import com.instructure.canvasapi.model.ModuleObject;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ModuleAPI extends BuildInterfaceAPI {

    interface ModulesInterface {
        @GET("/{context_id}/modules")
        void getFirstPageModuleObjects(@Path("context_id") long context_id, Callback<ModuleObject[]> callback);

        @GET("/{next}")
        void getNextPageModuleObjectList(@Path(value = "next", encode = false) String nextURL, Callback<ModuleObject[]> callback);

        @GET("/{context_id}/modules/{moduleid}/items?include[]=content_details")
        void getFirstPageModuleItems(@Path("context_id") long context_id, @Path("moduleid") long moduleID, Callback<ModuleItem[]> callback);

        @GET("/{next}?include[]=content_details")
        void getNextPageModuleItemList(@Path(value = "next", encode = false) String nextURL, Callback<ModuleItem[]> callback);

        @POST("/{context_id}/modules/{moduleid}/items/{itemid}/mark_read")
        void markModuleItemRead(@Path("context_id") long context_id, @Path("moduleid") long module_id, @Path("itemid") long item_id, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageModuleObjects(CanvasContext canvasContext, CanvasCallback<ModuleObject[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleObjects(canvasContext.getId(), callback);
        buildInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleObjects(canvasContext.getId(), callback);
    }

    public static void getNextPageModuleObjects(String nextURL, CanvasCallback<ModuleObject[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildCacheInterface(ModulesInterface.class, callback, false).getNextPageModuleObjectList(nextURL, callback);
        buildInterface(ModulesInterface.class, callback, false).getNextPageModuleObjectList(nextURL, callback);
    }

    public static void getFirstPageModuleItems(CanvasContext canvasContext, long moduleId, CanvasCallback<ModuleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildCacheInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleItems(canvasContext.getId(), moduleId, callback);
        buildInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleItems(canvasContext.getId(), moduleId, callback);
    }

    public static void getNextPageModuleItems(String nextURL, CanvasCallback<ModuleItem[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildCacheInterface(ModulesInterface.class, callback, false).getNextPageModuleItemList(nextURL, callback);
        buildInterface(ModulesInterface.class, callback, false).getNextPageModuleItemList(nextURL, callback);
    }

    public static void getNextPageModuleItemsChained(String nextURL, CanvasCallback<ModuleItem[]> callback, boolean isCached){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        if (isCached) {
            buildCacheInterface(ModulesInterface.class, callback, false).getNextPageModuleItemList(nextURL, callback);
        } else {
            buildInterface(ModulesInterface.class, callback, false).getNextPageModuleItemList(nextURL, callback);
        }
    }

    public static void markModuleItemRead(CanvasContext canvasContext, long moduleId, long itemId, CanvasCallback<Response> callback){
        if(APIHelpers.paramIsNull(callback, canvasContext)){
            return;
        }

        buildInterface(ModulesInterface.class, callback, canvasContext).markModuleItemRead(canvasContext.getId(), moduleId, itemId, callback);
    }
}
