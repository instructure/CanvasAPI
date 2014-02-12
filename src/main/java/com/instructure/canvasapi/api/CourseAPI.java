package com.instructure.canvasapi.api;

import android.content.Context;
import com.instructure.canvasapi.model.Course;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

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
        @GET("/courses/{courseid}?include[]=term&include[]=permissions")
        void getCourse(@Path("courseid") long courseId, CanvasCallback<Course> callback);

        @GET("/courses/{courseid}?include[]=syllabus_body&include[]=term")
        void getCourseWithSyllabus(@Path("courseid") long courseId, CanvasCallback<Course> callback);

        // I don't see why we wouldn't want to always get the grades
        @GET("/courses?include[]=term&include[]=total_scores")
        void getCourses(CanvasCallback<Course[]> callback);

        @GET("/courses?include[]=term&include[]=total_scores")
        Course[] getCourses();

        @GET("/courses?include[]=term&include[]=total_scores&enrollment_type=student")
        void getStudentCourses(CanvasCallback<Course[]> callback);

        @GET("/users/self/favorites/courses?include[]=term&include[]=total_scores")
        void getFavoriteCourses(CanvasCallback<Course[]> callback);
    }

    private static CoursesInterface buildInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(CoursesInterface.class);
    }

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

    public static Course[] getCoursesSynchronous(Context context) {
        if(context == null){
            return null;
        }
        
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);

        //If not able to parse (no network for example), this will crash. Handle that case.
        try {
            return restAdapter.create(CoursesInterface.class).getCourses();
        } catch (Exception E){
            return null;
        }
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

    public static Map<Long, Course> createCourseMap(Course[] courses) {
        Map<Long, Course> courseMap = new HashMap<Long, Course>();
        for (Course course : courses) {
            courseMap.put(course.getId(), course);
        }
        return courseMap;
    }
}
