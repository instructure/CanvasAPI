package com.instructure.canvasapi.utilities;

import com.instructure.canvasapi.api.CourseAPI;
import com.instructure.canvasapi.model.Course;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Josh Ruesch on 3/4/14.
 */

/*
    This class is used as a 'bridge' to get all the pages of the course api.
    It'll grab all pages from the course api and then return the ENTIRE results to the passed in callback.
 */

public class ExhaustiveCourseBridgeCallback extends CanvasCallback<Course[]> {

    //This is the callback that will get results once ALL the courses have been returned.
    CanvasCallback<Course[]> finalCourseCallback;
    ArrayList<Course> allCourses = new ArrayList<Course>();

    public ExhaustiveCourseBridgeCallback(APIStatusDelegate apiStatusDelegate, CanvasCallback<Course[]> finalCourseCallback){
        super(apiStatusDelegate);
        this.finalCourseCallback = finalCourseCallback;
    }


    @Override
    public void cache(Course[] courses) {}

    @Override
    public void firstPage(Course[] courses, LinkHeaders linkHeaders, Response response) {
        String nextURL = linkHeaders.nextURL;
        Collections.addAll(allCourses, courses);

        if(nextURL == null){
            Course[] allCoursesArray = new Course[allCourses.size()];
            allCoursesArray = allCourses.toArray(allCoursesArray);

            finalCourseCallback.success(allCoursesArray, response);
        } else{
            CourseAPI.getNextPageCourses(this, nextURL);
        }
    }

    @Override
    public void failure(RetrofitError retrofitError){
        finalCourseCallback.failure(retrofitError);
    }
}
