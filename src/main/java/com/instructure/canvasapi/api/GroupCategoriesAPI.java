package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.Group;
import com.instructure.canvasapi.model.GroupCategory;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Hoa Hoang on 9/25/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class GroupCategoriesAPI {

    private static String getUsersForGroupCategoryCacheName(long id) {
        return "/group_categories/" +String.valueOf(id) +"/users";
    }

    private static String getFirstPageGroupCategoriesCacheName(long courseId) {
        return "/courses/" + String.valueOf(courseId) + "/group_categories";
    }

    private static String getFirstPageGroupsFromCategoryCacheName(long categoryId) {
        return "/group_categories/" + String.valueOf(categoryId) + "/groups";
    }

    private static String getFirstPageUsersFromCategoryCacheName(long categoryId) {
        return "/group_categories/" + String.valueOf(categoryId) + "/users";
    }

    interface GroupCategoriesInterface {

        @GET("/group_categories/{group_categories_id}/users")
        void getUsersForGroupCategory(@Path("group_categories_id") long groupCategoryId, CanvasCallback<User[]> callback);

        @GET("/courses/{course_id}/group_categories")
        void getFirstPageGroupCategories(@Path("course_id") long courseId, CanvasCallback<GroupCategory[]> callback);

        @GET("/{next}")
        void getNextPageGroupCategories(@Path(value = "next", encode = false) String nextURL, Callback<GroupCategory[]> callback);

        @POST("/courses/{course_id}/group_categories")
        void createGroupCategoryForCourse(@Path("course_id") long courseId, @Query("name") String name, CanvasCallback<GroupCategory> callback);

        @GET("/group_categories/{group_category_id}/groups")
        void getFirstPageGroupsFromCategory(@Path("group_category_id") long groupCategoryId, CanvasCallback<Group[]> callback);

        @GET("/group_categories/{group_category_id}/users")
        void getFirstPageUsersInCategory(@Path("group_category_id") long groupCategoryId, @Query("unassigned") boolean onlyIncludeUnassigned, CanvasCallback<User[]> callback);

        @GET("/{next}")
        void getNextPageUsersInCategory(@Path(value = "next", encode = false) String nextURL, Callback<User[]> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////
    private static GroupCategoriesInterface buildInterface(CanvasCallback<?> callback) {
        return buildInterface(callback.getContext());
    }
    private static GroupCategoriesInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(GroupCategoriesInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////
    public static void getFirstPageGroupsInCourse(long groupCategoryId, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getUsersForGroupCategoryCacheName(groupCategoryId));
        buildInterface(callback).getUsersForGroupCategory(groupCategoryId, callback);
    }

    public static void createGroupCategoryForCourse(long courseId, String name, CanvasCallback<GroupCategory> callback) {
        if(APIHelpers.paramIsNull(name, callback)) return;

        buildInterface(callback).createGroupCategoryForCourse(courseId, name, callback);
    }

    public static void getFirstPageGroupCategoriesInCourse(long courseId, CanvasCallback<GroupCategory[]> callback) {
        if(APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageGroupCategoriesCacheName(courseId));
        buildInterface(callback).getFirstPageGroupCategories(courseId, callback);
    }

    public static void getNextPageGroupCategoriesInCourse(String nextURL, CanvasCallback<GroupCategory[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageGroupCategories(nextURL, callback);
    }

    public static void getFirstPageGroupsFromCategory(long groupCategoryId, CanvasCallback<Group[]> callback) {
        if(APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageGroupsFromCategoryCacheName(groupCategoryId));
        buildInterface(callback).getFirstPageGroupsFromCategory(groupCategoryId, callback);
    }

    public static void getFirstPageUsersInCategory(long groupCategoryId, boolean onlyIncludeUnassigned, CanvasCallback<User[]> callback) {
        if(APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageUsersFromCategoryCacheName(groupCategoryId));
        buildInterface(callback).getFirstPageUsersInCategory(groupCategoryId, onlyIncludeUnassigned, callback);
    }

    public static void getNextPageUsersInCategory(String nextURL, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageUsersInCategory(nextURL, callback);
    }
}
