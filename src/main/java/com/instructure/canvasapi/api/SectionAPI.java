package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Course;
import com.instructure.canvasapi.model.Section;
import com.instructure.canvasapi.model.Submission;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Josh Ruesch on 2/10/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class SectionAPI {

    private static String getFirstPageSectionCacheFilename(long courseID){
        return "/courses/" + courseID +"/sections";
    }

    private static String getCourseSectionsWithStudents(long courseID){
        return "/courses/" + courseID +"/sections?include[]=user";
    }

    private static String getAssignmentSubmissionForSectionCacheFilename(long section_id, long assignment_id){
        return "/sections/" + section_id + "/assignments/" + assignment_id + "/submissions";
    }

    interface SectionsInterface {

        @PUT("{courseid}/sections/{sectionid}")
        void updateSection(@Path("courseid") long courseID, @Path("sectionid") long sectionID,
                @Query("course_section[name]") String name,
                @Query("course_section[start_at]") String startAt, @Query("course_section[end_at]") String endAt,
                CanvasCallback<Section> callback
        );

        @GET("/{courseid}/sections")
        void getFirstPageSectionsList(@Path("courseid") long courseID, Callback<Section[]> callback);

        @GET("/{courseid}/sections?include[]=students")
        void getCourseSectionsWithStudents(@Path("courseid") long courseID, Callback<Section[]> callback);

        @GET("/{next}")
        void getNextPageSectionsList(@EncodedPath("next") String nextURL, Callback<Section[]> callback);

        @GET("/{section_id}/assignments/{assignment_id}/submissions")
        void getAssignmentSubmissionsForSection(@Path("section_id") long section_id, @Path("assignment_id") long assignment_id, Callback<Submission[]> callback);
    }


    private static SectionsInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(SectionsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////
    public static void getFirstPageSectionsList(Course course, CanvasCallback<Section[]> callback) {
        if (APIHelpers.paramIsNull(callback, course)) { return; }

        callback.readFromCache(getFirstPageSectionCacheFilename(course.getId()));
        buildInterface(callback, course).getFirstPageSectionsList(course.getId(), callback);
    }

    public static void getCourseSectionsWithStudents(Course course, CanvasCallback<Section[]> callback){
        if (APIHelpers.paramIsNull(callback, course)) { return; }

        callback.readFromCache(getCourseSectionsWithStudents(course.getId()));
        buildInterface(callback, course).getCourseSectionsWithStudents(course.getId(), callback);
    }

    public static void getNextPageSectionsList(String nextURL, CanvasCallback<Section[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageSectionsList(nextURL, callback);
    }

    public static void getAssignmentSubmissionsForSection(CanvasContext canvasContext, long assignment_id, final CanvasCallback<Submission[]> callback){
        if(APIHelpers.paramIsNull(callback, canvasContext)){return;}

            callback.readFromCache(getAssignmentSubmissionForSectionCacheFilename(canvasContext.getId(), assignment_id));
            buildInterface(callback, canvasContext).getAssignmentSubmissionsForSection(canvasContext.getId(), assignment_id, callback);
    }

    /**
     *
     * @param newSectionName (Optional)
     * @param newStartAt (Optional)
     * @param newEndAt (Optional)
     * @param course (Required)
     * @param section (Required)
     * @param callback (Required)
     */
    public static void updateSection(String newSectionName, Date newStartAt, Date newEndAt, Course course, Section section, CanvasCallback<Section> callback){
        if(APIHelpers.paramIsNull(callback, course, section)){return;}


        String startAtString = APIHelpers.dateToString(newStartAt);
        String endAtString = APIHelpers.dateToString(newEndAt);

        buildInterface(callback,course).updateSection(course.getId(), section.getId(), newSectionName, startAtString, endAtString, callback);

    }


}
