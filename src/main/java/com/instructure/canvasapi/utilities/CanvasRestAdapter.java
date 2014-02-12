package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instructure.canvasapi.model.CanvasContext;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author      Josh Ruesch
 * @since       08/10/13
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class CanvasRestAdapter {

    /**
     * Returns a RestAdapter Instance that points at :domain/api/v1
     *
     * @param  callback A Canvas Callback
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(CanvasCallback callback) {
        callback.setFinished(false);
        return buildAdapter(callback.getContext());
    }

    /**
     * Returns a RestAdapter Instance
     *
     * @param  context An Android context.
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context) {

        if(context == null ){
            return null;
        }

        String domain = APIHelpers.getFullDomain(context);

        //Can make this check as we KNOW that the setter doesn't allow empty strings.
        if (domain == null || domain.equals("")) {
            Log.d(APIHelpers.LOG_TAG, "The RestAdapter hasn't been set up yet. Call setupInstance(context,token,domain)");
            return new RestAdapter.Builder().setServer("http://invalid.domain.com").build();
        }

        GsonConverter gsonConverter = new GsonConverter(getGSONParser());

        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setServer(domain + "/api/v1/") // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context))
                .setConverter(gsonConverter)
                .build();

    }


    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     *
     * @param  context An Android context.
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context, CanvasContext canvasContext) {

        //If not return an adapter with no context.
        if(canvasContext == null){
            return buildAdapter(context);
        }

        //Check for null values or invalid CanvasContext types.
        if(context == null) {
            return null;
        }

        String domain = APIHelpers.getFullDomain(context);

        //Can make this check as we KNOW that the setter doesn't allow empty strings.
        if (domain == null || domain.equals("")) {
            Log.d(APIHelpers.LOG_TAG, "The RestAdapter hasn't been set up yet. Call setupInstance(context,token,domain)");
            return new RestAdapter.Builder().setServer("http://invalid.domain.com").build();
        }

        String apiContext;
        if(canvasContext.getType() == CanvasContext.Type.COURSE){
            apiContext = "courses/";
        } else if (canvasContext.getType() == CanvasContext.Type.GROUP) {
            apiContext = "groups/";
        } else {
            apiContext = "users/";
        }

        GsonConverter gsonConverter = new GsonConverter(getGSONParser());

        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setServer(domain + "/api/v1/" + apiContext) // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context))
                .setConverter(gsonConverter)
                .build();

    }

    /**
     * Returns a RestAdapter Instance that points at :domain/
     *
     * Used ONLY in the login flow!
     *
     * @param  context An Android context.
     */
    public static RestAdapter buildTokenRestAdapter(final Context context){

        if(context == null ){
            return null;
        }

        String domain = APIHelpers.getFullDomain(context);

        return new RestAdapter.Builder()
                .setServer(domain) // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context))
                .build();
    }


    /**
     * Returns a RestAdapter Instance that points at :domain/
     *
     * Used ONLY in the login flow!
     *
     * @param  context An Android context.
     */
    public static RestAdapter buildTokenRestAdapter(final String token, final String protocol, final String domain){

        if(token == null || protocol == null || domain == null ){
            return null;
        }



        return new RestAdapter.Builder()
                .setServer(protocol + "://" + domain) // The base API endpoint.
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade requestFacade) {
                        requestFacade.addHeader("Authorization", "Bearer " + token);
                    }
                })
                .build();
    }


    /**
     * Class that's used as to inject the user agent, token, and handles masquerading.
     */

    public static class CanvasRequestInterceptor implements RequestInterceptor{

        Context context;

        CanvasRequestInterceptor(Context context){
            this.context = context;
        }

        @Override
        public void intercept(RequestFacade requestFacade) {

            final String token = APIHelpers.getToken(context);
            final String userAgent = APIHelpers.getUserAgent(context);

            //Set the UserAgent
            if(userAgent != null && !userAgent.equals(""))
                requestFacade.addHeader("User-Agent", userAgent);

            //Authenticate if possible
            if(token != null && !token.equals("")){
                requestFacade.addHeader("Authorization", "Bearer " + token);
            }

            //Masquerade if necessary
            if (Masquerading.isMasquerading(context)) {
                requestFacade.addQueryParam("as_user_id", Long.toString(Masquerading.getMasqueradingId(context)));
            }
        }
    }



    /**
     * Gets our custom GSON parser.
     *
     * @return Our custom GSON parser with custom deserializers.
     */

    public static Gson getGSONParser(){
        GsonBuilder b = new GsonBuilder();
        //TODO:Register custom parsers here!
        return b.create();
    }


    /**
     * Sets up the CanvasRestAdapter.
     *
     * Short hand for setdomain, setToken, and setProtocol.
     *
     * Clears out any old data before setting the new data.
     *
     * @param context An Android context.
     * @param token An OAuth2 Token
     * @param domain The domain for the signed in user.
     *
     * @return Whether or not the instance was setup. Only returns false if the data is empty or invalid.
     */
    public static boolean setupInstance(Context context, String token, String domain){
        if (token == null ||
                token.equals("") ||
                domain == null) {

            return false;
        }

        String protocol = "https";
        if(domain.startsWith("http://")) {
            protocol = "http";
        }
        return (APIHelpers.setDomain(context, domain) && APIHelpers.setToken(context, token) && APIHelpers.setProtocol(protocol, context));
    }
}