package com.instructure.canvasapi.api;

import android.content.Context;
import android.text.TextUtils;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ScheduleItem;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Brady Larson on 10/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class CalendarEventAPI extends BuildInterfaceAPI {

    public enum EVENT_TYPE {CALENDAR_EVENT, ASSIGNMENT_EVENT;

        public static String getEventTypeName(EVENT_TYPE eventType) {
            if(eventType == CALENDAR_EVENT) {
                return "event";
            } else {
                return "assignment";
            }
        }
    }

    private static String getCalendarEventCacheFilename(long eventID) {
        return "/calendar_events/" + eventID;
    }

    private static String getCalendarEventsCacheFilename(CanvasContext canvasContext) {
        return "/calendar_events?start_date=1990-01-01&end_date=2099-12-31&context_codes[]=" + canvasContext.getId();
    }

    private static String getUpcomingEventsCacheFilename(){
        return "/users/self/upcoming_events";
    }

    private static String getAllEventsCacheFilename(String startDate, EVENT_TYPE eventType){
        String resultString = startDate.replaceAll("[^\\p{L}\\p{Nd}]+", "");
        return "/users/self/all" + eventType.name() + resultString.substring(0, 7);
    }

    private static String getAllCalendarEventsCacheFilename(String contextIds, EVENT_TYPE eventType){
        int lengthLimit = contextIds.length();
        if (lengthLimit > 30) {
            lengthLimit = 30;
        }
        return "/calendar_events?all_events=1&type=" + eventType.name() + "&" + contextIds.substring(0, lengthLimit); // limit the filename length
    }

    public interface CalendarEventsInterface {
        @GET("/calendar_events/{event_id}")
        void getCalendarEvent(@Path("event_id") long event_id, Callback<ScheduleItem> callback);

        @GET("/calendar_events?start_date=1990-01-01&end_date=2099-12-31")
        void getCalendarEvents(@Query("context_codes[]") String context_id, Callback<ScheduleItem[]> callback);

        @GET("/users/self/upcoming_events")
        void getUpcomingEvents(Callback<ScheduleItem[]> callback);

        @GET("/{next}")
        void getNextPageCalendarEvents(@Path(value = "next", encode = false) String nextURL, Callback<ScheduleItem[]> callback);

        @GET("/calendar_events/")
        void getCalendarEvents(
                @Query("all_events") boolean allEvents,
                @Query("type") String type,
                @EncodedQuery("context_codes[]") String contextCodes,
                Callback<ScheduleItem[]> callback);

        @GET("/calendar_events/")
        void getCalendarEvents(
                @Query("all_events") boolean allEvents,
                @Query("type") String type,
                @Query("start_date") String startDate,
                @Query("end_date") String endDate,
                @EncodedQuery("context_codes[]") String contextCodes,
                Callback<ScheduleItem[]> callback);

        @POST("/calendar_events/")
        void createCalendarEvent(@Query("calendar_event[context_code]") String contextCode,
                                 @Query("calendar_event[title]") String title,
                                 @Query("calendar_event[description]") String description,
                                 @Query("calendar_event[start_at]") String startDate,
                                 @Query("calendar_event[end_at]") String endDate,
                                 @Query("calendar_event[location_name]") String locationName,
                                 CanvasCallback<ScheduleItem> callback);

        @DELETE("/calendar_events/{event_id}")
        void deleteCalendarEvent(@Path("event_id") long event_id, @Query("cancel_reason") String cancelReason,
                                 CanvasCallback<ScheduleItem> callback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/users/self/upcoming_events")
        ScheduleItem[] getUpcomingEvents();
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getCalendarEvent(long calendarEventId, final CanvasCallback<ScheduleItem> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildCacheInterface(CalendarEventsInterface.class, callback).getCalendarEvent(calendarEventId, callback);
        buildInterface(CalendarEventsInterface.class, callback).getCalendarEvent(calendarEventId, callback);
    }

    public static void getCalendarEvents(CanvasContext canvasContext, final CanvasCallback<ScheduleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(CalendarEventsInterface.class, callback).getCalendarEvents(canvasContext.getContextId(), callback);
        buildInterface(CalendarEventsInterface.class, callback).getCalendarEvents(canvasContext.getContextId(), callback);
    }

    public static void getUpcomingEvents(final CanvasCallback<ScheduleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildCacheInterface(CalendarEventsInterface.class, callback).getUpcomingEvents(callback);
        buildInterface(CalendarEventsInterface.class, callback).getUpcomingEvents(callback);
    }

    public static void getNextPageCalendarEventsChained(String nextURL, CanvasCallback<ScheduleItem[]> callback, boolean isCached){
        if(APIHelpers.paramIsNull(callback, nextURL)){ return;}

        callback.setIsNextPage(true);
        if (isCached) {
            buildCacheInterface(CalendarEventsInterface.class, callback, false).getNextPageCalendarEvents(nextURL, callback);
        } else {
            buildInterface(CalendarEventsInterface.class, callback, false).getNextPageCalendarEvents(nextURL, callback);
        }
    }

    public static void getAllCalendarEventsExhaustive(EVENT_TYPE eventType, String startDate, String endDate, ArrayList<String> canvasContextIds, final CanvasCallback<ScheduleItem[]> callback) {
        String contextIds = buildContextArray(canvasContextIds);
        CanvasCallback<ScheduleItem[]> bridge = new ExhaustiveBridgeCallback<>(ScheduleItem.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL, boolean isCached) {
                if(callback.isCancelled()) { return; }

                CalendarEventAPI.getNextPageCalendarEventsChained(nextURL, bridgeCallback, isCached);
            }
        });

        buildCacheInterface(CalendarEventsInterface.class, callback).getCalendarEvents(false, EVENT_TYPE.getEventTypeName(eventType), startDate, endDate, contextIds, bridge);
        buildInterface(CalendarEventsInterface.class, callback).getCalendarEvents(false, EVENT_TYPE.getEventTypeName(eventType), startDate, endDate, contextIds, bridge);
    }

    public static void getAllCalendarEventsExhaustive(EVENT_TYPE eventType, ArrayList<String> canvasContextIds, final CanvasCallback<ScheduleItem[]> callback) {
        String contextIds = buildContextArray(canvasContextIds);

        CanvasCallback<ScheduleItem[]> bridge = new ExhaustiveBridgeCallback<>(ScheduleItem.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL, boolean isCached) {
                if(callback.isCancelled()) { return; }
                CalendarEventAPI.getNextPageCalendarEventsChained(nextURL, bridgeCallback, isCached);
            }
        });

        buildCacheInterface(CalendarEventsInterface.class, callback).getCalendarEvents(true, EVENT_TYPE.getEventTypeName(eventType), contextIds, bridge);
        buildInterface(CalendarEventsInterface.class, callback).getCalendarEvents(true, EVENT_TYPE.getEventTypeName(eventType), contextIds, bridge);
    }

    public static void createCalendarEvent(String contextCode, String title, String description, String startDate, String endDate, String location, final CanvasCallback<ScheduleItem> callback){
        if(APIHelpers.paramIsNull(callback, contextCode) || TextUtils.isEmpty(startDate) || TextUtils.isEmpty(endDate)){return;}

        buildInterface(CalendarEventsInterface.class, callback).createCalendarEvent(contextCode, title, description, startDate, endDate, location, callback);
    }

    public static void deleteCalendarEvent(long calendarEventId, String cancelReason, CanvasCallback<ScheduleItem> callback){
        if(APIHelpers.paramIsNull(callback)){return;}

        buildInterface(CalendarEventsInterface.class, callback).deleteCalendarEvent(calendarEventId, cancelReason, callback);
    }

    private static String buildContextArray(ArrayList<String> canvasContextIds){
        //Builds an array of context_codes, the way we have to build and send the array is funky.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < canvasContextIds.size(); i++) {
            sb.append(canvasContextIds.get(i));

            if(i == canvasContextIds.size() - 1) {
                break;
            }
            sb.append("&context_codes[]=");
        }

        return sb.toString();
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////


    public static ScheduleItem[] getUpcomingEventsSynchronous(Context context) {
        try {
            return buildInterface(CalendarEventsInterface.class, context).getUpcomingEvents();
        } catch (Exception E){
            return null;
        }
    }
}
