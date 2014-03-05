package com.instructure.canvasapi.api;

import android.content.Context;
import com.instructure.canvasapi.model.Course;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joshua Dutton on 9/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class CourseAPI {



    private static String getCourseCacheFilename(long courseId) {
        return "/courses/" + courseId;
    }

    private static String getCoursesCacheFilename() {
        return "/courses";
    }

    private static String getStudentCoursesWithGradesCacheFilename() {
        return "/student_courses";
    }

    private static String getFavoriteCoursesCacheFilename() {
        return "/users/self/favorites/courses";
    }

    interface CoursesInterface {

        @PUT("/courses/{courseid}")
        void updateCourse(@Path("courseid") long courseID,
                          @Query("course[name]") String name, @Query("course[course_code]") String courseCode,
                          @Query("course[start_at]")String startAt, @Query("course[end_at]")String endAt,
                          @Query("course[license]") String license, @Query("course[is_public]") Integer isPublic,
                          CanvasCallback<Course> callback);

        @GET("/courses/{courseid}?include[]=term&include[]=permissions&include[]=license&include[]=is_public")
        void getCourse(@Path("courseid") long courseId, CanvasCallback<Course> callback);

        @GET("/courses/{courseid}?include[]=syllabus_body&include[]=term&include[]=license&include[]=is_public")
        void getCourseWithSyllabus(@Path("courseid") long courseId, CanvasCallback<Course> callback);

        // I don't see why we wouldn't want to always get the grades
        @GET("/courses?include[]=term&include[]=total_scores&include[]=license&include[]=is_public")
        void getCourses(CanvasCallback<Course[]> callback);

        @GET("/courses?include[]=term&include[]=total_scores&enrollment_type=student&include[]=license&include[]=is_public")
        void getStudentCourses(CanvasCallback<Course[]> callback);

        @GET("/users/self/favorites/courses?include[]=term&include[]=total_scores&include[]=license&include[]=is_public")
        void getFavoriteCourses(CanvasCallback<Course[]> callback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////
        @GET("/courses?include[]=term&include[]=total_scores&include[]=license&include[]=is_public")
        Course[] getCourses();
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static CoursesInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(CoursesInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getCourse(long courseId, CanvasCallback<Course> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getCourseCacheFilename(courseId));
        buildInterface(callback).getCourse(courseId, callback);
    }

    public static void getCourseWithSyllabus(long courseId, CanvasCallback<Course> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getCourseCacheFilename(courseId));
        buildInterface(callback).getCourseWithSyllabus(courseId, callback);
    }

    public static void getCourses(CanvasCallback<Course[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getCoursesCacheFilename());
        buildInterface(callback).getCourses(callback);
    }

    public static void getStudentCourses(CanvasCallback<Course[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getStudentCoursesWithGradesCacheFilename());
        buildInterface(callback).getStudentCourses(callback);
    }

    public static void getFavoriteCourses(CanvasCallback<Course[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFavoriteCoursesCacheFilename());
        buildInterface(callback).getFavoriteCourses(callback);
    }

    /**
     *
     * @param newCourseName (Optional)
     * @param newCourseCode (Optional)
     * @param newStartAt    (Optional)
     * @param newEndAt      (Optional)
     * @param license       (Optional)
     * @param newIsPublic   (Optional)
     * @param course        (Required)
     * @param callback      (Required)
     */
    public static void updateCourse(String newCourseName, String newCourseCode, Date newStartAt, Date newEndAt, Course.LICENSE license, Boolean newIsPublic,Course course, CanvasCallback<Course>callback){
        if(APIHelpers.paramIsNull(callback, course)) {return;}

        String newStartAtString = APIHelpers.dateToString(newStartAt);
        String newEndAtString = APIHelpers.dateToString(newEndAt);
        Integer newIsPublicInteger = (newIsPublic == null) ? null : APIHelpers.booleanToInt(newIsPublic);

        buildInterface(callback).updateCourse(course.getId(), newCourseName, newCourseCode, newStartAtString, newEndAtString, Course.licenseToAPIString(license), newIsPublicInteger, callback);
    }


    /////////////////////////////////////////////////////////////////////////////
    // Helper Methods
    ////////////////////////////////////////////////////////////////////////////
    public static Map<Long, Course> createCourseMap(Course[] courses) {
        Map<Long, Course> courseMap = new HashMap<Long, Course>();
        for (Course course : courses) {
            courseMap.put(course.getId(), course);
        }
        return courseMap;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////

    public static Course[] getCoursesSynchronous(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);

        //If not able to parse (no network for example), this will crash. Handle that case.
        try {
            return restAdapter.create(CoursesInterface.class).getCourses();
        } catch (Exception E){
            return null;
        }
    }
}
