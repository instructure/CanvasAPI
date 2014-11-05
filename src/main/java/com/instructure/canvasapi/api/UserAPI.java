package com.instructure.canvasapi.api;

import android.util.Log;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Enrollment;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.*;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.*;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UserAPI {

    public enum ENROLLMENT_TYPE {STUDENT, TEACHER, TA, OBSERVER, DESIGNER}

    private static String getSelfEnrollmentsCacheFilename(){
        return "/users/self/enrollments";
    }

    private static String getUserByIdCacheFilename(long userId){
        return "/search/recipients/"+userId;
    }

    private static String getFirstPagePeopleCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/users";
    }
    private static String getFirstPagePeopleCacheFilename(CanvasContext canvasContext, ENROLLMENT_TYPE enrollment_type){
        return canvasContext + "/users/"+enrollment_type;
    }


    interface UsersInterface {
        @GET("/users/self/profile")
        void getSelf(Callback<User> callback);

        // TODO: We probably need to create a helper that does each of these individually
        @GET("/users/self/enrollments?state[]=active&state[]=invited&state[]=completed")
        void getSelfEnrollments(Callback<Enrollment[]> callback);

        @PUT("/users/self")
        void updateShortName(@Query("user[short_name]") String shortName, Callback<User> callback);

        @GET("/users/{userid}/profile")
        void getUserById(@Path("userid")long userId, Callback<User> userCallback);

        @GET("/{context_id}/users?include[]=enrollments&include[]=avatar_url&include[]=user_id&include[]=email")
        void getFirstPagePeopleList(@Path("context_id") long context_id, Callback<User[]> callback);

        @GET("/{context_id}/users?include[]=enrollments&include[]=avatar_url&include[]=user_id&include[]=email")
        void getFirstPagePeopleListWithEnrollmentType(@Path("context_id") long context_id, @Query("enrollment_role") String enrollmentType, Callback<User[]> callback);

        @GET("/{next}")
        void getNextPagePeopleList(@Path("next") String nextURL, Callback<User[]> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static UsersInterface buildInterface(CanvasCallback callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(UsersInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getSelf(UserCallback callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        //Read cache
        callback.cache(APIHelpers.getCacheUser(callback.getContext()));

        //Don't allow this API call to be made while masquerading.
        //It causes the current user to be overriden with the masqueraded one.
        if (Masquerading.isMasquerading(callback.getContext())) {
            Log.w(APIHelpers.LOG_TAG,"No API call for /users/self/profile can be made while masquerading.");
            return;
        }

        buildInterface(callback, null).getSelf(callback);
    }

    public static void getSelfEnrollments(CanvasCallback<Enrollment[]> callback) {
        if(APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getSelfEnrollmentsCacheFilename());
        buildInterface(callback, null).getSelfEnrollments(callback);
    }


    public static void updateShortName(String shortName, CanvasCallback<User> callback) {
        if (APIHelpers.paramIsNull(callback, shortName)) { return; }

        buildInterface(callback, null).updateShortName(shortName, callback);
    }

    public static void getUserById(long userId, CanvasCallback<User> userCanvasCallback){
        if(APIHelpers.paramIsNull(userCanvasCallback)){return;}

        userCanvasCallback.readFromCache(getUserByIdCacheFilename(userId));

        //Passing UserCallback here will break OUR cache.
        if(userCanvasCallback instanceof UserCallback){
            Log.e(APIHelpers.LOG_TAG, "You cannot pass a User Call back here. It'll break cache for users/self..");
            return;
        }

        buildInterface(userCanvasCallback, null).getUserById(userId,userCanvasCallback);
    }

    public static void getFirstPagePeople(CanvasContext canvasContext, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPagePeopleCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPagePeopleList(canvasContext.getId(), callback);
    }

    public static void getNextPagePeople(String nextURL, CanvasCallback<User[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPagePeopleList(nextURL, callback);
    }

    public static void getFirstPagePeople(CanvasContext canvasContext, ENROLLMENT_TYPE enrollment_type, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPagePeopleCacheFilename(canvasContext, enrollment_type));

        String enrollmentType;
        switch (enrollment_type){
            case DESIGNER:
                enrollmentType = "DesignerEnrollment";
                break;
            case OBSERVER:
                enrollmentType = "ObserverEnrollment";
                break;
            case STUDENT:
                enrollmentType = "StudentEnrollment";
                break;
            case TA:
                enrollmentType = "TaEnrollment";
                break;
            case TEACHER:
                enrollmentType = "TeacherEnrollment";
                break;
            default:
                return;
        }

        buildInterface(callback, canvasContext).getFirstPagePeopleListWithEnrollmentType(canvasContext.getId(), enrollmentType, callback);
    }
}
