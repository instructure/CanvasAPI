package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.AlertThreshold;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

/**
 * Copyright (c) 2016 Instructure. All rights reserved.
 */

public class AlertThresholdAPI extends BuildInterfaceAPI {



    interface AlertThresholdInterface {

        @GET("/alertthreshold/student/{observerId}/{studentId}")
        void getAlertThresholdsForStudent(@Path("observerId") long observerId, @Path("studentId") long studentId, Callback<AlertThreshold[]> callback);

        @GET("/alertthreshold/{observerId}/{thresholdId}")
        void getAlertThresholdById(@Path("observerId") long observerId, @Path("thresholdId") long thresholdId, Callback<AlertThreshold> callback);

        @FormUrlEncoded
        @PUT("/alertthreshold")
        void createAlertThreshold(@Field("observer_id") long observerId, @Field("student_id") long studentId, @Field("alert_type") String alertType, @Field("threshold") String threshold, CanvasCallback<AlertThreshold> callback);

        @FormUrlEncoded
        @POST("/alertthreshold/{observerId}/{thresholdId}")
        void updateAlertThreshold(@Path("observerId") long observerIdPath, @Path("thresholdId") String thresholdIdPath, @Field("observer_id") long observerId, @Field("threshold_id") String thresholdId, @Field("alert_type") String alertType, @Field("threshold") String threshold, CanvasCallback<AlertThreshold> callback);

        //threshold field is optional
        @FormUrlEncoded
        @PUT("/alertthreshold")
        void createAlertThreshold(@Field("observer_id") long observerId, @Field("student_id") long studentId, @Field("alert_type") String alertType, CanvasCallback<AlertThreshold> callback);

        //threshold field is optional
        @FormUrlEncoded
        @POST("/alertthreshold/{observerId}/{thresholdId}")
        void updateAlertThreshold(@Path("observerId") long observerIdPath, @Path("thresholdId") String thresholdIdPath, @Field("observer_id") long observerId, @Field("threshold_id") String thresholdId, @Field("alert_type") String alertType, CanvasCallback<AlertThreshold> callback);


        @DELETE("/alertthreshold/{observerId}/{thresholdId}")
        void deleteAlertThreshold(@Path("observerId") long observerId, @Path("thresholdId") String thresholdId, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAlertThresholdsForStudent(long observerId, long studentId, final CanvasCallback<AlertThreshold[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }


        buildCacheInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdsForStudent(observerId, studentId, callback);
        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdsForStudent(observerId, studentId, callback);
    }

    public static void getAlertThresholdById(long observerId, long thresholdId, final CanvasCallback<AlertThreshold> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }


        buildCacheInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdById(observerId, thresholdId, callback);
        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdById(observerId, thresholdId, callback);
    }


    public static void createAlertThreshold(long observerId, long studentId, String alertType, String threshold, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(callback, alertType, threshold)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).createAlertThreshold(observerId, studentId, alertType, threshold, callback);
    }

    public static void updateAlertThreshold(long observerId, String thresholdId, String alertType, String threshold, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(callback, alertType, threshold)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).updateAlertThreshold(observerId, thresholdId, observerId, thresholdId, alertType, threshold, callback);
    }

    public static void createAlertThreshold(long observerId, long studentId, String alertType, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(callback, alertType)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).createAlertThreshold(observerId, studentId, alertType, callback);
    }

    public static void updateAlertThreshold(long observerId, String thresholdId, String alertType, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(callback, alertType)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).updateAlertThreshold(observerId, thresholdId, observerId, thresholdId, alertType, callback);
    }
    public static void deleteAlertThreshold(long observerId, String thresholdId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).deleteAlertThreshold(observerId, thresholdId, callback);
    }
}
