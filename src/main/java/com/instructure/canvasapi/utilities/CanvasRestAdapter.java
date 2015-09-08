package com.instructure.canvasapi.utilities;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instructure.canvasapi.model.CanvasContext;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.CacheControl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * @author      Josh Ruesch
 * @since       08/10/13
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class CanvasRestAdapter {

    private static int numberOfItemsPerPage = 30;

    private static OkClient okHttpClient;
    private static Context mContext;
    public static int getNumberOfItemsPerPage() {
        return numberOfItemsPerPage;
    }

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

    private static final Interceptor mCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            com.squareup.okhttp.Request request = chain.request();

            Response response = chain.proceed(request);

            // Re-write response CC header to force use of cache
            // Displayed cached data will always be followed by a response from the server with the latest data.
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=172800") //60*60*24*2 = 172,800 2 weeks; Essentially means cached data will only be valid offline for 2 weeks. When network is available, the cache is always updated on every request.
                    .build();
        }
    };

    public static OkClient getOkHttp(Context context) {
        if (okHttpClient == null) {
            mContext = context;
            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
            Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024); // cache size
            OkHttpClient httpClient = new OkHttpClient();
            httpClient.setCache(cache);
            /** Dangerous interceptor that rewrites the server's cache-control header. */
            httpClient.networkInterceptors().add(mCacheControlInterceptor);
            okHttpClient = new OkClient(httpClient);

        }
        return okHttpClient;
    }

    /**
     * Returns a RestAdapter Instance
     *
     * @param context An Android context.
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context, final boolean addPerPageQueryParam) {
        return buildAdapter(context, false, addPerPageQueryParam);
    }

    public static RestAdapter buildAdapter(final Context context, final boolean isForcedCache, final boolean addPerPageQueryParam) {

        if (context == null) {
            return null;
        }

        if (context instanceof APIStatusDelegate) {
            ((APIStatusDelegate) context).onCallbackStarted();
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
                .setRequestInterceptor(new CanvasRequestInterceptor(context, addPerPageQueryParam, isForcedCache))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(gsonConverter)
                .setClient(getOkHttp(context)).build();
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

    public static RestAdapter buildAdapter(CanvasCallback callback, boolean isForceCache, CanvasContext canvasContext) {
        callback.setFinished(false);
        return buildAdapterHelper(callback.getContext(), canvasContext, isForceCache, true);
    }

    /**
     * Returns a RestAdapter instance that points at :domain/api/v1/groups or :domain/api/v1/courses depending on the CanvasContext
     **
     * @param callback A Canvas Callback
     * @param addPerPageQueryParam Specify if you want to add the per page query param
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(CanvasCallback callback, boolean addPerPageQueryParam) {
        callback.setFinished(false);
        return buildAdapter(callback.getContext(), addPerPageQueryParam);
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
        return buildAdapterHelper(context, canvasContext, false, addPerPageQueryParam);

    }
    private static RestAdapter buildAdapterHelper(final Context context, CanvasContext canvasContext, boolean isForcedCache, boolean addPerPageQueryParam) {
        //If not return an adapter with no context.
        if(canvasContext == null){
            return buildAdapter(context, isForcedCache, addPerPageQueryParam);
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
        /* TODO
        final OkClient httpClient = new OkClient(){
            @Override
            protected HttpURLConnection openConnection(Request request) throws IOException {
                HttpURLConnection connection = super.openConnection(request);
                connection.setReadTimeout(60 * 1000);
                return connection;
            }
        };
        */

        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setEndpoint(domain + "/api/v1/" + apiContext) // The base API endpoint.
                .setRequestInterceptor(new CanvasRequestInterceptor(context, addPerPageQueryParam, isForcedCache))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setConverter(gsonConverter)
                .setClient(getOkHttp(context)).build();
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
        boolean isForcedCache;

        CanvasRequestInterceptor(Context context, boolean addPerPageQueryParam){
            this.context = context;
            this.addPerPageQueryParam = addPerPageQueryParam;

        }

        CanvasRequestInterceptor(Context context, boolean addPerPageQueryParam, boolean isForcedCache){
            this.context = context;
            this.addPerPageQueryParam = addPerPageQueryParam;
            this.isForcedCache = isForcedCache;
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

            if (isForcedCache) {
                requestFacade.addHeader("Cache-Control", "only-if-cached");
            } else {
                requestFacade.addHeader("Cache-Control", "no-cache");
            }
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

    private static boolean isNetworkAvaliable(Context context) {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
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