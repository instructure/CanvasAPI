package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.NotificationPreferenceResponse;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedOutput;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class NotificationPreferencesAPI {

    //Frequency keys
    public static final String IMMEDIATELY = "immediately";
    public static final String DAILY = "daily";
    public static final String WEEKLY = "weekly";
    public static final String NEVER = "never";

    public interface NotificationPreferencesInterface {

        @GET("/users/{user_id}/communication_channels/{communication_channel_id}/notification_preferences")
        void getNotificationPreferences(@Path("user_id") long userId, @Path("communication_channel_id") long communicationChannelId, CanvasCallback<NotificationPreferenceResponse> callback);

        @GET("/users/{user_id}/communication_channels/{type}/{address}/notification_preferences")
        void getNotificationPreferencesForType(@Path("user_id") long userId, @Path("type") String type, @Path("address") String address, CanvasCallback<NotificationPreferenceResponse> callback);

        @GET("/users/{user_id}/communication_channels/{communication_channel_id}/notification_preferences/{notification}")
        void getSingleNotificationPreference(@Path("user_id") long userId, @Path("communication_channel_id") long communicationChannelId, @Path("notification") String notification, CanvasCallback<NotificationPreferenceResponse> callback);

        @GET("/users/{user_id}/communication_channels/{type}/{address}/notification_preferences/{notification}")
        void getSingleNotificationPreferencesForType(@Path("user_id") long userId, @Path("type") String type, @Path("address") String address, @Path("notification") String notification, CanvasCallback<NotificationPreferenceResponse> callback);

        @PUT("/users/self/communication_channels/{communication_channel_id}/notification_preferences/{notification}")
        void updateSingleNotificationPreference(@Path("communication_channel_id") long communicationChannelId, @Path("notification") String notification, CanvasCallback<NotificationPreferenceResponse> callback);

        @PUT("/users/self/communication_channels/{type}/{address}/notification_preferences/{notification}")
        void updateSingleNotificationPreferenceForType(@Path("type") String type, @Path("address") String address, @Path("notification") String notification, CanvasCallback<NotificationPreferenceResponse> callback);

        @PUT("/users/self/communication_channels/{type}/{address}/notification_preferences{notification_preferences}")
        void updateMultipleNotificationPreferencesForType(@Path("type") String type, @Path("address") String address, @Path(value = "notification_preferences", encode = false) String notifications, CanvasCallback<NotificationPreferenceResponse> callback);

        @PUT("/users/self/communication_channels/{communication_channel_id}/notification_preferences{notification_preferences}")
        void updateMultipleNotificationPreferences(@Path("communication_channel_id") long communicationChannelId, @Path(value = "notification_preferences", encode = false) String notifications, CanvasCallback<NotificationPreferenceResponse> callback);

        @PUT("/users/self/communication_channels/{communication_channel_id}/notification_preferences")
        void updateMultipleNotificationPreferences(@Path("communication_channel_id") long communicationChannelId, @Body NotificationPreferenceResponse preferencesToChange, CanvasCallback<NotificationPreferenceResponse> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static NotificationPreferencesInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext, false, false);
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return restAdapter.create(NotificationPreferencesInterface.class);
    }

    public static void getNotificationPreferences(final long userId, final long communicationChannelId, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getNotificationPreferences(userId, communicationChannelId, callback);
    }

    public static void getNotificationPreferencesByType(final long userId, final String type, final String address, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getNotificationPreferencesForType(userId, type, address, callback);
    }

    public static void getSingleNotificationPreference(final long userId, final long communicationChannelId, final String notification, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getSingleNotificationPreference(userId, communicationChannelId, notification, callback);
    }

    public static void getSingleNotificationPreferencesForType(final long userId, final String type, final String address, final String notification, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).getSingleNotificationPreferencesForType(userId, type, address, notification, callback);
    }

    public static void updateSingleNotificationPreference(final long communicationChannelId, final String notification, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).updateSingleNotificationPreference(communicationChannelId, notification, callback);
    }

    public static void updateSingleNotificationPreferenceForType(final String type, final String address, final String notification, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).updateSingleNotificationPreferenceForType(type, address, notification, callback);
    }

    public static void updateMultipleNotificationPreferencesForType(final String type, final String address, final ArrayList<String> notifications, final String frequency, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback, null).updateMultipleNotificationPreferencesForType(type, address, buildNotificationPreferenceList(notifications, frequency), callback);
    }

    public static void updateMultipleNotificationPreferences(final long communicationChannelId, final ArrayList<String> notifications, final String frequency, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback, notifications, frequency)) { return; }

        buildInterface(callback, null).updateMultipleNotificationPreferences(communicationChannelId, buildNotificationPreferenceList(notifications, frequency), callback);
    }

    /**
     * Updates multiple notifications. Typically used for updating large sets of notification preferences
     * @param communicationChannelId The id of the communication channel
     * @param preferencesToChange A list NotificationPreference objects to update, NOTE: frequency should already be set to the desired change.
     * @param callback A canvas callback
     */
    public static void updateMultipleNotificationPreferences(final long communicationChannelId, final NotificationPreferenceResponse preferencesToChange, final CanvasCallback<NotificationPreferenceResponse> callback) {
        if (APIHelpers.paramIsNull(callback, preferencesToChange)) { return; }

        buildInterface(callback, null).updateMultipleNotificationPreferences(communicationChannelId, preferencesToChange, callback);
    }

    private static String buildNotificationPreferenceList(ArrayList<String> notifications, String frequency) {

        StringBuilder builder = new StringBuilder();
        builder.append("?");
        for(String preference : notifications) {
            builder.append("notification_preferences");
            builder.append("[");
            builder.append(preference);
            builder.append("]");
            builder.append("[frequency]");
            builder.append("=");
            builder.append(frequency);
            builder.append("&");
        }

        String notificationsString = builder.toString();
        if(notificationsString.endsWith("&")) {
            notificationsString = notificationsString.substring(0, notificationsString.length() - 1);
        }
        return notificationsString;
    }
}
