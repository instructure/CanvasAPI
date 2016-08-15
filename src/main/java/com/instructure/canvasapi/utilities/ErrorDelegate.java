package com.instructure.canvasapi.utilities;

import android.content.Context;

import com.instructure.canvasapi.model.CanvasError;

import retrofit.RetrofitError;

/**
 * Created by Hoa Hoang on 10/18/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public interface ErrorDelegate {
    // No Network
    public void noNetworkError(RetrofitError error, Context context);

    // HTTP 401
    public void notAuthorizedError(RetrofitError error,CanvasError canvasError, Context context);

    // HTTP 400-500
    public void invalidUrlError(RetrofitError error, Context context);

    // HTTP 500-600
    public void serverError(RetrofitError error, Context context);

    // HTTP 200 OK but unknown error or an unexpected error in the retrofit client.
    public void generalError(RetrofitError error, CanvasError canvasError, Context context);
}