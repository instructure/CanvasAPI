package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Assignment;
import com.instructure.canvasapi.model.AssignmentGroup;
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
 * Created by Brady Larson on 9/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class AssignmentAPI {

    private static String getAssignmentCacheFilename(long courseID, long assignmentID) {
        return "/courses/" + courseID + "/assignments/" + assignmentID;
    }

    private static String getAssignmentsListCacheFilename(long courseID) {
        return "/courses/" + courseID + "/assignments?include=submission";
    }

    private static String getAssignmentGroupsListCacheFilename(long courseID) {
        return  "/courses/" + courseID + "/assignments_groups";
    }



    public interface AssignmentsInterface {
        @GET("/courses/{course_id}/assignments/{assignmentid}")
        void getAssignment(@Path("course_id") long course_id, @Path("assignmentid") long assignment_id, Callback<Assignment> callback);

        @GET("/courses/{course_id}/assignments?include[]=submission&include[]=rubric_assessment")
        void getAssignmentsList(@Path("course_id") long course_id, Callback<Assignment[]> callback);

        @GET("/courses/{course_id}/assignment_groups")
        void getAssignmentGroupList(@Path("course_id") long course_id, Callback<AssignmentGroup[]> callback);

        @GET("/calendar_events/{event_id}")
        void getCalendarEvent(@Path("event_id") long event_id, Callback<ScheduleItem> callback);

        @GET("/calendar_events?start_date=1990-01-01&end_date=2099-12-31")
        void getCalendarEvents(@Query("context_codes[]") String context_id, Callback<ScheduleItem[]> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static AssignmentsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(AssignmentsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getAssignment(long courseID, long assignmentID, final CanvasCallback<Assignment> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildInterface(callback, null).getAssignment(courseID, assignmentID, callback);
    }

    public static void getAssignmentsList(long courseID, final CanvasCallback<Assignment[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentsListCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentsList(courseID, callback);
    }

    public static void getAssignmentGroupsList(long courseID, final CanvasCallback<AssignmentGroup[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getAssignmentGroupsListCacheFilename(courseID));
        buildInterface(callback, null).getAssignmentGroupList(courseID, callback);
    }



}
