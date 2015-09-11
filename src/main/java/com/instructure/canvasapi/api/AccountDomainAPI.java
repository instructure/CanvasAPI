package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.AccountDomain;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */

public class AccountDomainAPI {

    private static final String DEFAULT_DOMAIN = "canvas.instructure.com";

    public interface AccountDomainInterface {

        @GET("/accounts/search")
        void getFirstPageAccountDomains(CanvasCallback<AccountDomain[]> callback);

        @GET("/{next}")
        void getNextPageAccountDomains(@Path(value = "next", encode = false) String nextURL, CanvasCallback<AccountDomain[]> callback);

        @GET("/accounts/search")
        void searchAccountDomains(@Query("name") String campusName, @Query("domain") String domain, @Query("latitude") float latitude, @Query("longitude") float longitude, CanvasCallback<AccountDomain[]> callback);

    }


    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AccountDomainInterface buildInterface(CanvasCallback<?> callback) {
        //set the domain here so we have a domain for the api call
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(AccountDomainInterface.class);
    }

    public static void searchAccountDomains(String campusName, String domain, float latitude, float longitude, final CanvasCallback<AccountDomain[]> callback) {
        if (APIHelpers.paramIsNull(campusName, domain, callback)) { return; }
        APIHelpers.setDomain(callback.getContext(), DEFAULT_DOMAIN);

        buildInterface(callback).searchAccountDomains(campusName, domain, latitude, longitude, callback);
    }

    public static void getFirstPageAccountDomains(CanvasCallback<AccountDomain[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;
        APIHelpers.setDomain(callback.getContext(), DEFAULT_DOMAIN);

        buildInterface(callback).getFirstPageAccountDomains(callback);
    }

    public static void getNextPageAccountDomains(CanvasCallback<AccountDomain[]> callback, String nextURL) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageAccountDomains(nextURL, callback);
    }

    public static void getAllAccountDomains(final CanvasCallback<AccountDomain[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        CanvasCallback<AccountDomain[]> bridge = new ExhaustiveBridgeCallback<>(callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL) {
                if(callback.isCancelled()) { return; }

                AccountDomainAPI.getNextPageAccountDomains(bridgeCallback, nextURL);
            }

            @Override
            public Class classType() {
                return AccountDomain.class;
            }
        });

        getFirstPageAccountDomains(bridge);
    }

}
