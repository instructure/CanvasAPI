package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ModuleItem;
import com.instructure.canvasapi.model.ModuleObject;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;
import com.squareup.okhttp.Response;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class ModuleAPI extends BuildInterfaceAPI {

    interface ModulesInterface {
        @GET("/{context_id}/modules")
        void getFirstPageModuleObjects(@Path("context_id") long context_id, Callback<ModuleObject[]> callback);

        @GET("/{next}")
        void getNextPageModuleObjectList(@Path(value = "next", encode = false) String nextURL, Callback<ModuleObject[]> callback);

        @GET("/{context_id}/modules/{module_id}/items?include[]=content_details&include[]=mastery_paths")
        void getFirstPageModuleItems(@Path("context_id") long context_id, @Path("module_id") long moduleID, Callback<ModuleItem[]> callback);

        @GET("/{next}?include[]=content_details&include[]=mastery_paths")
        void getNextPageModuleItemList(@Path(value = "next", encode = false) String nextURL, Callback<ModuleItem[]> callback);

        @POST("/{context_id}/modules/{module_id}/items/{item_id}/mark_read")
        void markModuleItemRead(@Path("context_id") long context_id, @Path("module_id") long module_id, @Path("item_id") long item_id, @Body String body, Callback<Response> callback);

        @PUT("/{context_id}/modules/{module_id}/items/{item_id}/done")
        void markModuleAsDone(@Path("context_id") long context_id, @Path("module_id") long module_id, @Path("item_id") long item_id, @Body String body, Callback<Response> callback);

        @DELETE("/{context_id}/modules/{module_id}/items/{item_id}/done")
        void markModuleAsNotDone(@Path("context_id") long context_id, @Path("module_id") long module_id, @Path("item_id") long item_id, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getModuleItemsExhaustive(CanvasContext canvasContext, long moduleId, final CanvasCallback<ModuleItem[]> callback) {
        if (APIHelpers.paramIsNull(canvasContext, callback)) { return; }

        CanvasCallback<ModuleItem[]> bridge = new ExhaustiveBridgeCallback<>(ModuleItem.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL, boolean isCached) {
                if(callback.isCancelled()) { return; }

                ModuleAPI.getNextPageModuleItemsChained(nextURL, bridgeCallback, isCached);
            }
        });

        buildCacheInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleItems(canvasContext.getId(), moduleId, bridge);
        buildInterface(ModulesInterface.class, callback, canvasContext).getFirstPageModuleItems(canvasContext.getId(), moduleId, bridge);
    }

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

        buildInterface(ModulesInterface.class, callback, canvasContext).markModuleItemRead(canvasContext.getId(), moduleId, itemId, "", callback);
    }

    public static void markModuleAsDone(CanvasContext canvasContext, long moduleId, long itemId, CanvasCallback<Response> callback){
        if(APIHelpers.paramIsNull(callback, canvasContext)){
            return;
        }

        buildInterface(ModulesInterface.class, callback, canvasContext, false).markModuleAsDone(canvasContext.getId(), moduleId, itemId, "", callback);
    }

    public static void markModuleAsNotDone(CanvasContext canvasContext, long moduleId, long itemId, CanvasCallback<Response> callback){
        if(APIHelpers.paramIsNull(callback, canvasContext)){
            return;
        }

        buildInterface(ModulesInterface.class, callback, canvasContext, false).markModuleAsNotDone(canvasContext.getId(), moduleId, itemId, callback);
    }
}
