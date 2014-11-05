package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.RestAdapter;
import retrofit.http.Path;
import retrofit.http.GET;

/**
 * Created by Hoa Hoang on 9/25/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class GroupCategoriesAPI {

    private static String getUsersForGroupCategoryCacheName(long id) {
        return "/group_categories/" +String.valueOf(id) +"/users";
    }

    interface GroupCategoriesInterface {

        @GET("/group_categories/{group_categories_id}/users")
        void getUsersForGroupCategory(@Path("group_categories_id") long groupCategoryId, CanvasCallback<User[]> callback);
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
}
