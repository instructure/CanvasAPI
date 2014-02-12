package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.util.Log;
import com.instructure.canvasapi.model.CanvasError;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.Serializable;

/**
 * CanvasCallback is a parameterized class that handles pagination and caching automatically.
 * Created by Joshua Dutton on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public abstract class CanvasCallback<T> implements Callback<T> {

    protected APIStatusDelegate statusDelegate;
    private String cacheFileName;
    private boolean isNextPage = false;
    private boolean isCancelled = false;
    private boolean isFinished;

    public static ErrorDelegate defaultErrorDelegate;
    private ErrorDelegate errorDelegate;

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @param apiStatusDelegate Delegate to get the context
     */
    public CanvasCallback(APIStatusDelegate apiStatusDelegate) {
        statusDelegate = apiStatusDelegate;


        this.errorDelegate = getDefaultErrorDelgate(getContext());

        if(this.errorDelegate == null){
            Log.e(APIHelpers.LOG_TAG, "WARNING: No ErrorDelegate Set.");
        }
    }

    /**
     * Overload constructor to override default error delegate.
     */
    public CanvasCallback(APIStatusDelegate apiStatusDelegate, ErrorDelegate errorDelegate){
        statusDelegate = apiStatusDelegate;
        this.errorDelegate = errorDelegate;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Returns the default Error Delegate
     * @param context
     * @return
     */
    public static ErrorDelegate getDefaultErrorDelgate(Context context){
        if(defaultErrorDelegate == null ){
            String defaultErrorDelegateClass = APIHelpers.getDefaultErrorDelegateClass(context);

            if(defaultErrorDelegateClass != null){
                try {
                    Class<?> errorDelegateClass = (Class.forName(defaultErrorDelegateClass));
                    defaultErrorDelegate = (ErrorDelegate) errorDelegateClass.newInstance();
                } catch (Exception E) {
                    Log.e(APIHelpers.LOG_TAG,"WARNING: Invalid defaultErrorDelegateClass Set: "+defaultErrorDelegateClass);
                }
            }
        }

        return defaultErrorDelegate;
    }

    private void finishLoading() {
        isFinished = true;
        statusDelegate.onCallbackFinished();
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * @return Current context, can be null
     */
    public Context getContext(){
        return statusDelegate.getContext();
    }


    /**
     * setShouldCache sets whether or not a call should be cached and the filename where it'll be cached to.
     * Should only be called by the API
     */
    public void setShouldCache(String fileName) {
        cacheFileName = fileName;
    }

    /**
     * shouldCache is a helper for whether or not a cacheFileName has been set.
     * @return
     */
    public boolean shouldCache() {
        return cacheFileName != null;
    }

    /**
     * setIsNextPage sets whether you're on the NextPages (2 or more) of pagination.
     * @param nextPage
     */
    public void setIsNextPage(boolean nextPage){
        isNextPage = nextPage;
    }

    /**
     * Intended to work as AsyncTask.cancel() does.
     * The network call is still made, but no response is made.
     *
     * Gotchas:
     *       Cache is still called.
     *       The callback has to be reinitialized as you can't 'uncancel'
     */
    public void cancel(){
        isCancelled = true;
    }

    /**
     * readFromCache reads from the cache filename and simultaneously sets the cache filename
     * @param path
     */
    public void readFromCache(String path) {
        try {
            Serializable serializable = FileUtilities.FileToSerializable(getContext(), path);
            if (serializable != null && getContext() != null) {
                cache((T) serializable);
            }
        } catch (Exception E) {
            Log.e(APIHelpers.LOG_TAG,"NO CACHE: " + path);
        }

        setShouldCache(path);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Interface
    ///////////////////////////////////////////////////////////////////////////

    /**
     * cache is a function you can override to get the cached values.
     * @param t
     */
    public abstract void cache(T t);

    /**
     * firstPage is the first (or only in some cases) of the API response.
     * @param t
     * @param linkHeaders
     * @param response
     */
    public abstract void firstPage(T t, LinkHeaders linkHeaders, Response response);

    /**
     *
     * nextPage is the second (or more) page of the API response.
     * Defaults to calling firstPage
     * Override if you want to change this functionality
     * @param t
     * @param linkHeaders
     * @param response
     */
    public void nextPage(T t, LinkHeaders linkHeaders, Response response){
        firstPage(t, linkHeaders, response);
    }

    /**
     * onFailure is a way to handle a failure instead using the
     * default error handling
     * @param retrofitError
     * @return true if the failure was handled, false otherwise
     */
    public boolean onFailure(RetrofitError retrofitError) {
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Retrofit callback methods
    ///////////////////////////////////////////////////////////////////////////

    /**
     * If you want caching and pagination, you must call this function using super or leave it alone..
     * @param t
     * @param response
     */
    @Override
    public void success(T t, Response response) {
        // check if it's been cancelled or detached
        if(isCancelled || t == null || getContext() == null) {
            return;
        }

        LinkHeaders linkHeaders = APIHelpers.parseLinkHeaderResponse(getContext(), response.getHeaders());

        if (shouldCache() && !isNextPage && getContext() != null) {
            try {
                Serializable serializable = (Serializable) t;
                FileUtilities.SerializableToFile(getContext(), cacheFileName, serializable);
            } catch (Exception E) {
            }
        }

        if(isNextPage){
            nextPage(t, linkHeaders, response);
        }else{
            firstPage(t, linkHeaders, response);
        }

        finishLoading();
    }

    /**
     * failure calls the correct method on the ErrorDelegate that's been set.
     * @param retrofitError
     */
    @Override
    public void failure(RetrofitError retrofitError) {
        // check if it's cancelled or detached
        if (isCancelled || getContext() == null) {
            return;
        }

        finishLoading();

        // Return if the failure was already handled
        if (onFailure(retrofitError)) {
            return;
        }

        if (errorDelegate == null) {
            Log.d(APIHelpers.LOG_TAG, "WARNING: No ErrorDelegate Provided ");
            return;
        }


        if (retrofitError.isNetworkError()) {
            if (retrofitError.getMessage() != null && retrofitError.getMessage().contains("authentication challenges")) {
                errorDelegate.notAuthorizedError(retrofitError, null, getContext());
            } else {
                errorDelegate.noNetworkError(retrofitError, getContext());
            }
        }


        Log.e(APIHelpers.LOG_TAG, "ERROR: " + retrofitError.getUrl());
        Log.e(APIHelpers.LOG_TAG, "ERROR: " + retrofitError.getMessage());

        CanvasError canvasError = null;
        try {
            canvasError = (CanvasError) retrofitError.getBodyAs(CanvasError.class);
        } catch (Exception exception) {
        }

        Response response = retrofitError.getResponse();
        if (response == null) {
            return;
        }

        Log.e(APIHelpers.LOG_TAG, "Response code: " + response.getStatus());
        Log.e(APIHelpers.LOG_TAG, "Response body: " + response.getBody());

        if (response.getStatus() == 200) {
            errorDelegate.generalError(retrofitError, canvasError, getContext());
        } else if (response.getStatus() == 401) {
            errorDelegate.notAuthorizedError(retrofitError, canvasError, getContext());
        } else if (response.getStatus() >= 400 && response.getStatus() < 500) {
            errorDelegate.invalidUrlError(retrofitError, getContext());
        } else if (response.getStatus() >= 500 && response.getStatus() < 600) {
            errorDelegate.serverError(retrofitError, getContext());
        }
    }
}
