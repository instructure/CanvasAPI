package com.instructure.canvasapi.api;

import android.content.Context;
import android.util.Log;

import com.instructure.canvasapi.model.Attachment;
import com.instructure.canvasapi.model.CanvasColor;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Enrollment;
import com.instructure.canvasapi.model.FileUploadParams;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;
import com.instructure.canvasapi.utilities.Masquerading;
import com.instructure.canvasapi.utilities.UserCallback;

import java.io.File;
import java.util.LinkedHashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.PartMap;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
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

    private static String getCourseUserListCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/users/all";
    }

    private static String getColorCacheFilename(){
        return "/users/colors";
    }

    private static String getSelfWithPermissionsCacheFileName(){
        return "/users/self";
    }

    interface UsersInterface {
        @GET("/users/self/profile")
        void getSelf(Callback<User> callback);

        // TODO: We probably need to create a helper that does each of these individually
        @GET("/users/self/enrollments?state[]=active&state[]=invited&state[]=completed")
        void getSelfEnrollments(Callback<Enrollment[]> callback);

        @GET("/users/self")
        void getSelfWithPermission(CanvasCallback<User> callback);

        @PUT("/users/self")
        void updateShortName(@Query("user[short_name]") String shortName, Callback<User> callback);

        @GET("/users/{userid}/profile")
        void getUserById(@Path("userid")long userId, Callback<User> userCallback);

        @GET("/{context_id}/users/{userId}?include[]=avatar_url&include[]=user_id&include[]=email&include[]=bio")
        void getUserById(@Path("context_id") long context_id, @Path("userId")long userId, Callback<User> userCallback);

        @GET("/{context_id}/users?include[]=enrollments&include[]=avatar_url&include[]=user_id&include[]=email&include[]=bio")
        void getFirstPagePeopleList(@Path("context_id") long context_id, Callback<User[]> callback);

        @GET("/{context_id}/users?include[]=enrollments&include[]=avatar_url&include[]=user_id&include[]=email")
        void getFirstPagePeopleListWithEnrollmentType(@Path("context_id") long context_id, @Query("enrollment_type") String enrollmentType, Callback<User[]> callback);

        @GET("/{next}")
        void getNextPagePeopleList(@Path(value = "next", encode = false) String nextURL, Callback<User[]> callback);

        @POST("/users/self/file")
        void uploadUserFileURL( @Query("url") String fileURL, @Query("name") String fileName, @Query("size") long size, @Query("content_type") String content_type, @Query("parent_folder_path") String parentFolderPath, Callback<String> callback);

        @POST("/users/self/files")
        FileUploadParams getFileUploadParams( @Query("size") long size, @Query("name") String fileName, @Query("content_type") String content_type, @Query("parent_folder_id") Long parentFolderId);

        @POST("/users/self/files")
        FileUploadParams getFileUploadParams( @Query("size") long size, @Query("name") String fileName, @Query("content_type") String content_type, @Query("parent_folder_path") String parentFolderPath);

        @Multipart
        @POST("/")
        Attachment uploadUserFile(@PartMap LinkedHashMap<String, String> params, @Part("file") TypedFile file);

        //Colors
        @GET("/users/self/colors")
        void getColors(CanvasCallback<CanvasColor> callback);

        @PUT("/users/self/colors/{context_id}")
        void setColor(@Path("context_id") String context_id, @Query(value = "hexcode", encodeValue = false) String color, CanvasCallback<CanvasColor> callback); 
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static UsersInterface buildInterface(CanvasCallback callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(UsersInterface.class);
    }

    private static UsersInterface buildInterface(CanvasCallback callback, CanvasContext canvasContext, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext, perPageQueryParam);
        return restAdapter.create(UsersInterface.class);
    }

    private static UsersInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(UsersInterface.class);
    }

    private static UsersInterface buildInterface(Context context, boolean perPageQueryParam) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, perPageQueryParam);
        return restAdapter.create(UsersInterface.class);
    }

    private static UsersInterface buildUploadInterface(String hostURL) {
        RestAdapter restAdapter = CanvasRestAdapter.getGenericHostAdapter(hostURL);
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

    public static void getSelfWithPermissions(CanvasCallback<User> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        //Don't allow this API call to be made while masquerading.
        //It causes the current user to be overriden with the masqueraded one.
        if (Masquerading.isMasquerading(callback.getContext())) {
            Log.w(APIHelpers.LOG_TAG,"No API call for /users/self can be made while masquerading.");
            return;
        }

        callback.readFromCache(getSelfWithPermissionsCacheFileName());

        buildInterface(callback, null).getSelfWithPermission(callback);
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

        buildInterface(userCanvasCallback, null).getUserById(userId, userCanvasCallback);
    }

    public static void getCourseUserById(CanvasContext canvasContext, long userId, CanvasCallback<User> userCanvasCallback){
        if(APIHelpers.paramIsNull(userCanvasCallback)){return;}

        userCanvasCallback.readFromCache(getUserByIdCacheFilename(userId));

        //Passing UserCallback here will break OUR cache.
        if(userCanvasCallback instanceof UserCallback){
            Log.e(APIHelpers.LOG_TAG, "You cannot pass a User Call back here. It'll break cache for users/self..");
            return;
        }

        buildInterface(userCanvasCallback, canvasContext).getUserById(canvasContext.getId(), userId, userCanvasCallback);
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

    public static void getAllUsersForCourseByEnrollmentType(CanvasContext canvasContext, ENROLLMENT_TYPE enrollment_type, final CanvasCallback<User[]> callback){
        if(APIHelpers.paramIsNull(callback, canvasContext)){return;}

        CanvasCallback<User[]> bridge = new ExhaustiveBridgeCallback<>(callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback bridgeCallback, String nextURL) {
                if(callback.isCancelled()) { return; }

                UserAPI.getNextPagePeople(nextURL, bridgeCallback);
            }

            @Override
            public Class classType() {
                return User.class;
            }
        });
        callback.readFromCache(getCourseUserListCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPagePeopleListWithEnrollmentType(canvasContext.getId(), getEnrollmentTypeString(enrollment_type), bridge);
    }

    public static void getFirstPagePeople(CanvasContext canvasContext, ENROLLMENT_TYPE enrollment_type, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPagePeopleCacheFilename(canvasContext, enrollment_type));

        buildInterface(callback, canvasContext).getFirstPagePeopleListWithEnrollmentType(canvasContext.getId(), getEnrollmentTypeString(enrollment_type), callback);
    }

    public static void getColors(Context context, CanvasCallback<CanvasColor> callback) {
        callback.readFromCache(getColorCacheFilename());
        buildInterface(context, false).getColors(callback);
    }

    public static void setColor(Context context, CanvasContext canvasContext, int color, CanvasCallback<CanvasColor> callback) {
        if (APIHelpers.paramIsNull(context, canvasContext, callback)) { return; }

        setColor(context, canvasContext.getContextId(), color, callback);
    }

    public static void setColor(Context context, String context_id, int color, CanvasCallback<CanvasColor> callback) {
        if (APIHelpers.paramIsNull(context, context_id, callback)) { return; }

        //Modifies a color into a RRGGBB color string with no #.
        String hexColor = Integer.toHexString(color);
        hexColor = hexColor.substring(hexColor.length() - 6);

        if(hexColor.contains("#")) {
            hexColor = hexColor.replaceAll("#", "");
        }

        buildInterface(context, false).setColor(context_id, hexColor, callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Synchronous Calls
    /////////////////////////////////////////////////////////////////////////
    public static FileUploadParams getFileUploadParams(Context context, String fileName, long size, String contentType, Long parentFolderId){
        return buildInterface(context).getFileUploadParams(size, fileName, contentType, parentFolderId);
    }

    public static FileUploadParams getFileUploadParams(Context context, String fileName, long size, String contentType, String parentFolderPath){
        return buildInterface(context).getFileUploadParams(size, fileName, contentType, parentFolderPath);
    }

    public static Attachment uploadUserFile(String uploadUrl, LinkedHashMap<String,String> uploadParams, String mimeType, File file){
        return buildUploadInterface(uploadUrl).uploadUserFile(uploadParams, new TypedFile(mimeType, file));
    }

    /////////////////////////////////////////////////////////////////////////
    // Helpers
    /////////////////////////////////////////////////////////////////////////
    private static String getEnrollmentTypeString(ENROLLMENT_TYPE enrollment_type){
        String enrollmentType = "";
        switch (enrollment_type){
            case DESIGNER:
                enrollmentType = "designer";
                break;
            case OBSERVER:
                enrollmentType = "observer";
                break;
            case STUDENT:
                enrollmentType = "student";
                break;
            case TA:
                enrollmentType = "ta";
                break;
            case TEACHER:
                enrollmentType = "teacher";
                break;
        }
        return enrollmentType;
    }
}
