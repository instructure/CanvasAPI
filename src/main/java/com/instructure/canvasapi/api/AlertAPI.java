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

    public static final String AIRWOLF_DOMAIN_AMERICA = "https://airwolf-iad-prod.instructure.com/";
    public static final String AIRWOLF_DOMAIN_DUBLIN = "https://airwolf-dub-prod.instructure.com/";
    public static final String AIRWOLF_DOMAIN_SYDNEY = "https://airwolf-syd-prod.instructure.com/";
    public static final String AIRWOLF_DOMAIN_SINGAPORE = "https://airwolf-sin-prod.instructure.com/";

    public static final String[] AIRWOLF_DOMAIN_LIST = { AIRWOLF_DOMAIN_AMERICA, AIRWOLF_DOMAIN_DUBLIN,
            AIRWOLF_DOMAIN_SYDNEY, AIRWOLF_DOMAIN_SINGAPORE };

    interface AlertInterface {
        @GET("/alerts/student/{parentId}/{studentId}")
        void getFirstPageOfAlertsForStudent(@Path("parentId") String parentId, @Path("studentId") String studentId, Callback<Alert[]> callback);

        @GET("/{next}")
        void getNextPageOfAlertsForStudent(@Path(value = "next", encode = false) String nextURL, CanvasCallback<Alert[]> callback);

        @FormUrlEncoded
        @POST("/alert/{parentId}/{alertId}")
        void markAlertAsRead(@Path("parentId") String parentId, @Path("alertId") String alertId, @Field("read") String isRead, CanvasCallback<Response> callback);

        @FormUrlEncoded
        @POST("/alert/{parentId}/{alertId}")
        void markAlertAsDismissed(@Path("parentId") String parentId, @Path("alertId") String alertId, @Field("dismissed") String isDismissed, CanvasCallback<Response> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAllAlertsForStudent(String parentId, String studentId, final CanvasCallback<Alert[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }

        CanvasCallback<Alert[]> bridge = new ExhaustiveBridgeCallback<>(Alert.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL, boolean isCached) {
                if(callback.isCancelled()) { return; }

                AlertAPI.getNextPageAlertsChained(bridgeCallback, nextURL, isCached);
            }
        });

        buildCacheInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback).getFirstPageOfAlertsForStudent(parentId, studentId, bridge);
        buildInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback).getFirstPageOfAlertsForStudent(parentId, studentId, bridge);
    }

    public static void getNextPageAlertsChained(CanvasCallback<Alert[]> callback, String nextURL, boolean isCached) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        if (isCached) {
            buildCacheInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback, false).getNextPageOfAlertsForStudent(nextURL, callback);
        } else {
            buildInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback, false).getNextPageOfAlertsForStudent(nextURL, callback);
        }
    }

    public static void markAlertAsRead(String parentId, String alertId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) return;


        buildInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback, false).markAlertAsRead(parentId, alertId, "true", callback);
    }

    public static void markAlertAsDismissed(String parentId, String alertId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        buildInterface(AlertInterface.class, APIHelpers.getAirwolfDomain(callback.getContext()), callback, false).markAlertAsDismissed(parentId, alertId, "true", callback);
    }
}
