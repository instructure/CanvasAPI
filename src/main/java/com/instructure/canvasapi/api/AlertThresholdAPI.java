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

        @GET("/alertthreshold/student/{parentId}/{studentId}")
        void getAlertThresholdsForStudent(@Path("parentId") String parentId, @Path("studentId") String studentId, Callback<AlertThreshold[]> callback);

        @GET("/alertthreshold/{parentId}/{thresholdId}")
        void getAlertThresholdById(@Path("parentId") String parentId, @Path("thresholdId") long thresholdId, Callback<AlertThreshold> callback);

        @FormUrlEncoded
        @PUT("/alertthreshold")
        void createAlertThreshold(@Field("observer_id") String parentId, @Field("student_id") String studentId, @Field("alert_type") String alertType, @Field("threshold") String threshold, CanvasCallback<AlertThreshold> callback);

        @FormUrlEncoded
        @POST("/alertthreshold/{parentId}/{thresholdId}")
        void updateAlertThreshold(@Path("parentId") String parentIdPath, @Path("thresholdId") String thresholdIdPath, @Field("observer_id") String parentId, @Field("threshold_id") String thresholdId, @Field("alert_type") String alertType, @Field("threshold") String threshold, CanvasCallback<AlertThreshold> callback);

        //threshold field is optional
        @FormUrlEncoded
        @PUT("/alertthreshold")
        void createAlertThreshold(@Field("observer_id") String parentId, @Field("student_id") String studentId, @Field("alert_type") String alertType, CanvasCallback<AlertThreshold> callback);

        //threshold field is optional
        @FormUrlEncoded
        @POST("/alertthreshold/{parentId}/{thresholdId}")
        void updateAlertThreshold(@Path("parentId") String parentIdPath, @Path("thresholdId") String thresholdIdPath, @Field("observer_id") String parentId, @Field("threshold_id") String thresholdId, @Field("alert_type") String alertType, CanvasCallback<AlertThreshold> callback);


        @DELETE("/alertthreshold/{parentId}/{thresholdId}")
        void deleteAlertThreshold(@Path("parentId") String parentId, @Path("thresholdId") String thresholdId, Callback<Response> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAlertThresholdsForStudent(String observerId, String studentId, final CanvasCallback<AlertThreshold[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }


        buildCacheInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdsForStudent(observerId, studentId, callback);
        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdsForStudent(observerId, studentId, callback);
    }

    public static void getAlertThresholdById(String parentId, long thresholdId, final CanvasCallback<AlertThreshold> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }


        buildCacheInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdById(parentId, thresholdId, callback);
        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback).getAlertThresholdById(parentId, thresholdId, callback);
    }


    public static void createAlertThreshold(String parentId, String studentId, String alertType, String threshold, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(callback, alertType, threshold)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).createAlertThreshold(parentId, studentId, alertType, threshold, callback);
    }

    public static void updateAlertThreshold(String parentId, String thresholdId, String alertType, String threshold, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(parentId, callback, alertType, threshold)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).updateAlertThreshold(parentId, thresholdId, parentId, thresholdId, alertType, threshold, callback);
    }

    public static void createAlertThreshold(String parentId, String studentId, String alertType, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(parentId, callback, alertType)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).createAlertThreshold(parentId, studentId, alertType, callback);
    }

    public static void updateAlertThreshold(String parentId, String thresholdId, String alertType, CanvasCallback<AlertThreshold> callback) {
        if (APIHelpers.paramIsNull(parentId, callback, alertType)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).updateAlertThreshold(parentId, thresholdId, parentId, thresholdId, alertType, callback);
    }
    public static void deleteAlertThreshold(String parentId, String thresholdId, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(parentId, callback)) return;

        buildInterface(AlertThresholdInterface.class, AlertAPI.AIRWOLF_DOMAIN, callback, false).deleteAlertThreshold(parentId, thresholdId, callback);
    }
}
