package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Alert;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */
public class AlertAPI extends BuildInterfaceAPI {

    public static final String AIRWOLF_DOMAIN = "http://airwolf-beta-web-306228951.us-west-2.elb.amazonaws.com/";

    interface AlertInterface {
        @GET("/alerts/student/{observerId}/{studentId}")
        void getFirstPageOfAlertsForStudent(@Path("observerId") long observerId, @Path("studentId") long studentId, Callback<Alert[]> callback);

        @GET("/{next}")
        void getNextPageOfAlertsForStudent(@Path(value = "next", encode = false) String nextURL, CanvasCallback<Alert[]> callback);

        @FormUrlEncoded
        @POST("/alert/{observerId}/{alertId}")
        void markAlertAsRead(@Path("observerId") long observerId, @Path("alertId") String alertId, @Field("read") String isRead, CanvasCallback<Response> callback);

        @FormUrlEncoded
        @POST("/alert/{observerId}/{alertId}")
        void markAlertAsDismissed(@Path("observerId") long observerId, @Path("alertId") String alertId, @Field("dismissed") String isDismissed, CanvasCallback<Response> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAllAlertsForStudent(long observerId, long studentId, final CanvasCallback<Alert[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }

        CanvasCallback<Alert[]> bridge = new ExhaustiveBridgeCallback<>(Alert.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL, boolean isCached) {
                if(callback.isCancelled()) { return; }

                AlertAPI.getNextPageAlertsChained(bridgeCallback, nextURL, isCached);
            }
        });

        buildCacheInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback).getFirstPageOfAlertsForStudent(observerId, studentId, bridge);
        buildInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback).getFirstPageOfAlertsForStudent(observerId, studentId, bridge);
    }

    public static void getNextPageAlertsChained(CanvasCallback<Alert[]> callback, String nextURL, boolean isCached) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        if (isCached) {
            buildCacheInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback, false).getNextPageOfAlertsForStudent(nextURL, callback);
        } else {
            buildInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback, false).getNextPageOfAlertsForStudent(nextURL, callback);
        }
    }

    public static void markAlertAsRead(long observerId, String alertId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) return;


        buildInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback, false).markAlertAsRead(observerId, alertId, "true", callback);
    }

    public static void markAlertAsDismissed(long observerId, String alertId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        buildInterface(AlertInterface.class, AIRWOLF_DOMAIN, callback, false).markAlertAsDismissed(observerId, alertId, "true", callback);
    }
}
