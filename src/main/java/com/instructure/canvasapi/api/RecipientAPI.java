package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Recipient;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;

import retrofit.Callback;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class RecipientAPI extends BuildInterfaceAPI {

    interface RecipientsInterface {
        @GET("/search/recipients?synthetic_contexts=1")
        void getFirstPageRecipientsList(@Query("search") String searchTerm, @EncodedQuery("context")String context, Callback<Recipient[]> callback);

        @GET("/search/recipients?synthetic_contexts=1")
        void getFirstPageRecipientsListNoContext(@Query("search") String searchTerm, Callback<Recipient[]> callback);

        @GET("/{next}")
        void getNextPageRecipientsList(@Path(value = "next", encode = false) String nextURL, Callback<Recipient[]> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageRecipients(String search, String context, CanvasCallback<Recipient[]> callback) {
        if (APIHelpers.paramIsNull(callback, search, context)) { return; }

        if(context.trim().equals("")){
            buildCacheInterface(RecipientsInterface.class, callback).getFirstPageRecipientsListNoContext(search,callback);
            buildInterface(RecipientsInterface.class, callback).getFirstPageRecipientsListNoContext(search,callback);
        } else {
            buildCacheInterface(RecipientsInterface.class, callback).getFirstPageRecipientsList(search,context,callback);
            buildInterface(RecipientsInterface.class, callback).getFirstPageRecipientsList(search,context,callback);
        }
    }

    public static void getNextPageRecipients(String nextURL, CanvasCallback<Recipient[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildCacheInterface(RecipientsInterface.class, callback, false).getNextPageRecipientsList(nextURL,callback);
        buildInterface(RecipientsInterface.class, callback, false).getNextPageRecipientsList(nextURL,callback);
    }
}
