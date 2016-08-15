package com.instructure.canvasapi.api;

import com.instructure.canvasapi.utilities.APIHelpers;

import retrofit.Callback;
import retrofit.Profiler;
import retrofit.client.Response;
import retrofit.http.GET;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class PingAPI extends BuildInterfaceAPI {

    interface PingInterface {
        @GET("/ping")
        void getPing(Callback<Response> callback);
    }

    public static void getPing(String url, Profiler profiler, Callback<Response> callback) {
        if (APIHelpers.paramIsNull(profiler)) { return; }

        buildPingInterface(PingInterface.class, url, profiler).getPing(callback);
    }
}
