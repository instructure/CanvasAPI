package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Recipient;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.EncodedPath;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;

/**
 * Created by Josh Ruesch on 10/16/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class RecipientAPI {

    interface RecipientsInterface {
        @GET("/search/recipients?synthetic_contexts=1")
        void getFirstPageRecipientsList(@EncodedQuery("search") String searchTerm, @EncodedQuery("context")String context, Callback<Recipient[]> callback);

        @GET("/search/recipients?synthetic_contexts=1")
        void getFirstPageRecipientsListNoContext(@EncodedQuery("search") String searchTerm, Callback<Recipient[]> callback);

        @GET("/{next}")
        void getNextPageRecipientsList(@EncodedPath("next") String nextURL, Callback<Recipient[]> callback);
    }

    private static RecipientsInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(RecipientsInterface.class);
    }

    public static void getFirstPageRecipients(String search, String context, CanvasCallback<Recipient[]> callback) {
        if (APIHelpers.paramIsNull(callback, search, context)) { return; }

        if(context.trim().equals("")){
            buildInterface(callback).getFirstPageRecipientsListNoContext(search,callback);
        } else {
            buildInterface(callback).getFirstPageRecipientsList(search,context,callback);
        }
    }

    public static void getNextPageRecipients(String nextURL, CanvasCallback<Recipient[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageRecipientsList(nextURL,callback);
    }
}
