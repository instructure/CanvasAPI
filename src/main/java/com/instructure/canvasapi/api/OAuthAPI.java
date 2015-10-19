package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.OAuthToken;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.EncodedQuery;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class OAuthAPI {


    interface OAuthInterface {
        @DELETE("/login/oauth2/token")
        void deleteToken(Callback<Response> callback);

        @POST("/login/oauth2/token")
        void getToken(@Query("client_id") String clientId, @Query("client_secret") String clientSecret, @Query("code") String oAuthRequest, @EncodedQuery("redirect_uri")String redirectURI, CanvasCallback<OAuthToken>canvasCallback);

    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void deleteToken(CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        RestAdapter restAdapter = CanvasRestAdapter.buildTokenRestAdapter(callback.getContext());
        OAuthInterface oAuthInterface = restAdapter.create(OAuthInterface.class);
        oAuthInterface.deleteToken(callback);
    }

    public static void deleteToken(String token, String protocol, String domain, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        RestAdapter restAdapter = CanvasRestAdapter.buildTokenRestAdapter(token, protocol, domain);
        OAuthInterface oAuthInterface = restAdapter.create(OAuthInterface.class);
        oAuthInterface.deleteToken(callback);
    }

    public static void getToken(String clientId, String clientSecret, String oAuthRequest, CanvasCallback<OAuthToken> callback) {

        if (APIHelpers.paramIsNull(callback,clientId,clientSecret,oAuthRequest)) { return; }

        RestAdapter restAdapter = CanvasRestAdapter.buildTokenRestAdapter(callback.getContext());
        OAuthInterface oAuthInterface = restAdapter.create(OAuthInterface.class);
        oAuthInterface.getToken(clientId, clientSecret, oAuthRequest, "urn:ietf:wg:oauth:2.0:oob", callback);
    }
}
