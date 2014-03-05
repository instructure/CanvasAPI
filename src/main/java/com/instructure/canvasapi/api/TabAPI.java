package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Tab;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Josh Ruesch on 12/30/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class TabAPI {

    private static String getTabsCacheFilename(CanvasContext canvasContext) {
        return canvasContext.toAPIString() + "/tabs";
    }

    interface TabsInterface {

        @PUT("{/context_id}/tabs{tab_id}")
        void updateTab(@Query("hidden") Integer hidden, @Query("position") Integer oneBasedIndexPosition, CanvasCallback<Tab> callback);

        @GET("/{context_id}/tabs?include[]=external")
        void getTabs(@Path("context_id") long context_id, CanvasCallback<Tab[]> callback);
    }

    private static TabsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(TabsInterface.class);
    }

    public static void getTabs(CanvasContext canvasContext, CanvasCallback<Tab[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) return;

        callback.readFromCache(getTabsCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getTabs(canvasContext.getId(), callback);
    }


    /**
     *
     * @param newIsHidden               (Optional)
     * @param newOneBasedIndexPosition  (Optional)
     * @param canvasContext             (Required)
     * @param tab                       (Required)
     * @param callback                  (Required)
     */
    public static void updateTab(Boolean newIsHidden, Integer newOneBasedIndexPosition, CanvasContext canvasContext, Tab tab, CanvasCallback<Tab>callback){
        if(APIHelpers.paramIsNull(callback,canvasContext,tab) || (newOneBasedIndexPosition != null && newOneBasedIndexPosition < 1)) {return;}

        Integer hiddenInteger = newIsHidden == null ? null : APIHelpers.booleanToInt(newIsHidden);

        buildInterface(callback,canvasContext).updateTab(hiddenInteger, newOneBasedIndexPosition, callback);
    }

}
