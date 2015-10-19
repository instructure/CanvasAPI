package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.RestAdapter;

public class BuildInterfaceAPI {

    public static <T> T buildCacheInterface(Class<T> clazz, CanvasCallback callback, CanvasContext canvasContext, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext, true, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    public static <T> T buildCacheInterface(Class<T> clazz, CanvasCallback callback, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, null, true, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    /**
     * Creates a rest adapter that will only read from the cache.
     */
    public static <T> T buildCacheInterface(Class<T> clazz, CanvasCallback callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, true, canvasContext);
        return restAdapter.create(clazz);
    }

    public static <T> T buildCacheInterface(Class<T> clazz, Context context, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, true, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    public static <T> T buildCacheInterface(Class<T> clazz, CanvasCallback callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, true, null);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, CanvasCallback callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, CanvasCallback callback, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, null, false, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, CanvasCallback callback, CanvasContext canvasContext, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext, false, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, Context context, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, perPageQueryParam);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, final Context context, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, canvasContext);
        return restAdapter.create(clazz);
    }

    public static <T> T buildInterface(Class<T> clazz, CanvasCallback callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(clazz);
    }

    public static <T> T buildUploadInterface(Class<T> clazz, String hostURL) {
        RestAdapter restAdapter = CanvasRestAdapter.getGenericHostAdapter(hostURL);
        return restAdapter.create(clazz);
    }

}
