package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.Group;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josh Ruesch on 12/27/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class GroupAPI {

    private static String getAllGroupsCacheFilename() {
        return "/users/self/groups";
    }

    private static String getGroupCacheFilename(long groupID) {
        return "/groups/"+groupID;
    }

    private static String getGroupsInCourseCacheFilename(long courseID) {
        return "/users/courses/"+courseID+"/groups";
    }

    interface GroupsInterface {
        @GET("/users/self/groups")
        void getAllGroups(CanvasCallback<Group[]> callback);

        @GET("/users/self/groups")
        Group[] getAllGroupsSynchronous();

        @GET("/courses/{courseid}/groups")
        void getAllGroupsInCourse(@Path("courseid") long courseId, CanvasCallback<Group[]> callback);

        @GET("/groups/{groupid}?include[]=permissions")
        void getDetailedGroup(@Path("groupid") long groupId, CanvasCallback<Group> callback);
    }


    private static GroupsInterface buildInterface(CanvasCallback<?> callback) {
        return buildInterface(callback.getContext());
    }
    private static GroupsInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(GroupsInterface.class);
    }

    public static void getAllGroups(CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getAllGroupsCacheFilename());
        buildInterface(callback).getAllGroups(callback);
    }

    public static Group[] getAllGroupsSynchronous(Context context) {


        try {
            return buildInterface(context).getAllGroupsSynchronous();
        } catch (Exception E) {
            return null;
        }
    }

    public static void getAllGroupsInCourse(long courseID, CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getGroupsInCourseCacheFilename(courseID));
        buildInterface(callback).getAllGroupsInCourse(courseID, callback);
    }

    public static void getDetailedGroup(long groupId, CanvasCallback<Group> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getGroupCacheFilename(groupId));
        buildInterface(callback).getDetailedGroup(groupId,callback);
    }


    public static Map<Long, Group> createGroupMap(Group[] groups) {
        Map<Long, Group> groupMap = new HashMap<Long, Group>();
        for (Group group : groups) {
            groupMap.put(group.getId(), group);
        }
        return groupMap;
    }
}
