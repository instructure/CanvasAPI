package com.instructure.canvasapi.api;

import android.content.Context;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ScheduleItem;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Brady Larson on 10/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class CalendarEventAPI {

    private static String getCalendarEventCacheFilename(long eventID) {
        return "/calendar_events/" + eventID;
    }

    private static String getCalendarEventsCacheFilename(CanvasContext canvasContext) {
        return "/calendar_events?start_date=1990-01-01&end_date=2099-12-31&context_codes[]=" + canvasContext.getId();
    }

    private static String getUpcomingEventsCacheFilename(){
        return "/users/self/upcoming_events";
    }

    public interface CalendarEventsInterface {
        @GET("/calendar_events/{event_id}")
        void getCalendarEvent(@Path("event_id") long event_id, Callback<ScheduleItem> callback);

        @GET("/calendar_events?start_date=1990-01-01&end_date=2099-12-31")
        void getCalendarEvents(@Query("context_codes[]") String context_id, Callback<ScheduleItem[]> callback);

        @GET("/users/self/upcoming_events")
        void getUpcomingEvents(Callback<ScheduleItem[]> callback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/users/self/upcoming_events")
        ScheduleItem[] getUpcomingEvents();
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static CalendarEventsInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(CalendarEventsInterface.class);
    }

    private static CalendarEventsInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(CalendarEventsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getCalendarEvent(long calendarEventId, final CanvasCallback<ScheduleItem> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getCalendarEventCacheFilename(calendarEventId));
        buildInterface(callback).getCalendarEvent(calendarEventId, callback);
    }

    public static void getCalendarEvents(CanvasContext canvasContext, final CanvasCallback<ScheduleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getCalendarEventsCacheFilename(canvasContext));
        buildInterface(callback).getCalendarEvents(canvasContext.getContextId(), callback);
    }

    public static void getUpcomingEvents(final CanvasCallback<ScheduleItem[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getUpcomingEventsCacheFilename());
        buildInterface(callback).getUpcomingEvents(callback);
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
            return buildInterface(context).getUpcomingEvents();
        } catch (Exception E){
            return null;
        }
    }
}
