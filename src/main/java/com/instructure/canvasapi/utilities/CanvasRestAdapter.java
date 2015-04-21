package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instructure.canvasapi.model.CanvasContext;
import java.io.IOException;
import java.net.HttpURLConnection;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.client.Request;
import retrofit.converter.GsonConverter;

/**
 * @author      Josh Ruesch
 * @since       08/10/13
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class CanvasRestAdapter {

    public static int numberOfItemsPerPage = 30;

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
     * Returns a RestAdapter Instance that points at :domain/api/v1
     *
     * @param context An Android context.
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context) {
        return buildAdapter(context, true);
    }

    /**
     * Returns a RestAdapter Instance
     *
     * @param context An Android context.
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context, final boolean addPerPageQueryParam) {

        if(context == null ){
            return null;
        }

        if (context instanceof APIStatusDelegate) {
            ((APIStatusDelegate)context).onCallbackStarted();
        }

        String domain = APIHelpers.getFullDomain(context);

        //Can make this check as we KNOW that the setter doesn't allow empty strings.
        if (domain == null || domain.equals("")) {
            Log.d(APIHelpers.LOG_TAG, "The RestAdapter hasn't been set up yet. Call setupInstance(context,token,domain)");
            return new RestAdapter.Builder().setEndpoint("http://invalid.domain.com").build();
        }

        GsonConverter gsonConverter = new GsonConverter(getGSONParser());

        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setEndpoint(domain + "/api/v1/") // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context, addPerPageQueryParam))
                .setConverter(gsonConverter)
                .build();

    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     *
     * @param callback A Canvas Callback
     * @param canvasContext A Canvas Context
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(CanvasCallback callback, CanvasContext canvasContext) {
        callback.setFinished(false);
        return buildAdapter(callback.getContext(), canvasContext);
    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     *
     * @param callback A Canvas Callback
     * @param canvasContext A Canvas Context
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(CanvasCallback callback, CanvasContext canvasContext, boolean addPerPageQueryParam) {
        callback.setFinished(false);
        return buildAdapter(callback.getContext(), canvasContext, addPerPageQueryParam);
    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     *
     * @param context An Android context.
     * @param canvasContext A Canvas Context
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context, CanvasContext canvasContext) {
        return buildAdapterHelper(context, canvasContext, true);
    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     * @param context An Android context.
     * @param canvasContext A Canvas Context
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return
     */
    public static RestAdapter buildAdapter(final Context context, CanvasContext canvasContext, boolean addPerPageQueryParam) {
        return buildAdapterHelper(context, canvasContext, addPerPageQueryParam);
    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     *
     * If CanvasContext is null, it returns an instance that simply points to :domain/api/v1/
     *
     * @param context An Android context.
     * @param canvasContext A Canvas Context
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    private static RestAdapter buildAdapterHelper(final Context context, CanvasContext canvasContext, boolean addPerPageQueryParam) {
        //If not return an adapter with no context.
        if(canvasContext == null){
            return buildAdapter(context, addPerPageQueryParam);
        }

        //Check for null values or invalid CanvasContext types.
        if(context == null) {
            return null;
        }

        if (context instanceof APIStatusDelegate) {
            ((APIStatusDelegate)context).onCallbackStarted();
        }

        String domain = APIHelpers.getFullDomain(context);

        //Can make this check as we KNOW that the setter doesn't allow empty strings.
        if (domain == null || domain.equals("")) {
            Log.d(APIHelpers.LOG_TAG, "The RestAdapter hasn't been set up yet. Call setupInstance(context,token,domain)");
            return new RestAdapter.Builder().setEndpoint("http://invalid.domain.com").build();
        }

        String apiContext;
        if(canvasContext.getType() == CanvasContext.Type.COURSE){
            apiContext = "courses/";
        } else if (canvasContext.getType() == CanvasContext.Type.GROUP) {
            apiContext = "groups/";
        } else if (canvasContext.getType() == CanvasContext.Type.SECTION){
            apiContext = "sections/";
        }else {
            apiContext = "users/";
        }

        GsonConverter gsonConverter = new GsonConverter(getGSONParser());

        // Set request timeout to 60 seconds
        final OkClient httpClient = new OkClient(){
            @Override
            protected HttpURLConnection openConnection(Request request) throws IOException {
                HttpURLConnection connection = super.openConnection(request);
                connection.setReadTimeout(60 * 1000);
                return connection;
            }
        };

        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setEndpoint(domain + "/api/v1/" + apiContext) // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context, addPerPageQueryParam))
                .setConverter(gsonConverter)
                .setClient(httpClient)
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
                .setEndpoint(domain) // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context, true))
                .build();
    }


    /**
     * Returns a RestAdapter Instance that points at :domain/
     *
     * Used ONLY in the login flow!
     *
     */
    public static RestAdapter buildTokenRestAdapter(final String token, final String protocol, final String domain){

        if(token == null || protocol == null || domain == null ){
            return null;
        }



        return new RestAdapter.Builder()
                .setEndpoint(protocol + "://" + domain) // The base API endpoint.
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade requestFacade) {
                        requestFacade.addHeader("Authorization", "Bearer " + token);
                    }
                })
                .build();
    }

    /**
     * Creates a new RestAdapter for a generic endpoint. Useful for 3rd party api calls such as amazon s3 uploads.
     * @param hostUrl : url for desired endpoint
     * @return
     */
    public static RestAdapter getGenericHostAdapter(String hostUrl){

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(hostUrl)
                .build();

        return restAdapter;
    }
    /**
     * Class that's used as to inject the user agent, token, and handles masquerading.
     */

    public static class CanvasRequestInterceptor implements RequestInterceptor{

        Context context;
        boolean addPerPageQueryParam;

        CanvasRequestInterceptor(Context context, boolean addPerPageQueryParam){
            this.context = context;
            this.addPerPageQueryParam = addPerPageQueryParam;
        }

        @Override
        public void intercept(RequestFacade requestFacade) {

            final String token = APIHelpers.getToken(context);
            final String userAgent = APIHelpers.getUserAgent(context);
            final String domain = APIHelpers.loadProtocol(context) + "://" + APIHelpers.getDomain(context);

            //Set the UserAgent
            if(userAgent != null && !userAgent.equals(""))
                requestFacade.addHeader("User-Agent", userAgent);

            //Authenticate if possible
            if(token != null && !token.equals("")){
                requestFacade.addHeader("Authorization", "Bearer " + token);
            }

            requestFacade.addHeader("Cache-Control", "no-cache");
            //HTTP referer (originally a misspelling of referrer) is an HTTP header field that identifies the address of the webpage that linked to the resource being requested
            //Source: https://en.wikipedia.org/wiki/HTTP_referer
            //Some schools use an LTI tool called SlideShare that whitelists domains to be able to inject content into assignments
            //They check the referrer in order to do this. 	203
            requestFacade.addHeader("Referer", domain);

            //Masquerade if necessary
            if (Masquerading.isMasquerading(context)) {
                requestFacade.addQueryParam("as_user_id", Long.toString(Masquerading.getMasqueradingId(context)));
            }

            if(addPerPageQueryParam) {
                //Sets the per_page count so we can get back more items with less round-trip calls.
                requestFacade.addQueryParam("per_page", Integer.toString(numberOfItemsPerPage));
            }
        }
    }

    /**
     * set a new default for the number of items returned per page.
     *
     * @param itemsPerPage
     */
    public static void setDefaultNumberOfItemsPerPage(int itemsPerPage) {
        if(itemsPerPage > 0){
            numberOfItemsPerPage = itemsPerPage;
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
     * @param itemsPerPage The number of items to return per page. Default is 30.
     * @return Whether or not the instance was setup. Only returns false if the data is empty or invalid.
     */
    public static boolean setupInstance(Context context, String token, String domain, int itemsPerPage){
        setDefaultNumberOfItemsPerPage(itemsPerPage);
        return setupInstance(context,token,domain);
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