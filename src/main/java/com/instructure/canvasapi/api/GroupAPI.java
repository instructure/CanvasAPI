package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.Group;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveGroupBridgeCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Josh Ruesch on 12/27/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class GroupAPI {

    private static String getAllGroupsCacheFilename() {
        return "/users/self/allgroups";
    }

    private static String getFirstPageGroupsCacheFilename() {
        return "/users/self/groups";
    }

    private static String getGroupCacheFilename(long groupID) {
        return "/groups/"+groupID;
    }

    private static String getFirstPageGroupsInCourseCacheFilename(long courseID) {
        return "/users/courses/"+courseID+"/groups";
    }

    private static String getAllGroupsInCourseCacheFilename(long courseID) {
        return "/users/courses/"+courseID+"/allgroups";
    }

    private static String getGroupUsersCacheFilename(long courseID) {
        return "/groups/"+courseID+"/users";
    }

    private static String getGroupUsersWithAvatarsCacheFilename(long courseID) {
        return "/groups/"+courseID+"/users?include[]=avatar_url";
    }

    interface GroupsInterface {
        @GET("/users/self/groups")
        void getFirstPageGroups(CanvasCallback<Group[]> callback);

        @GET("/courses/{courseid}/groups")
        void getFirstPageGroupsInCourse(@Path("courseid") long courseId, CanvasCallback<Group[]> callback);

        @GET("/{next}")
        void getNextPageGroups(@EncodedPath("next")String nextURL, CanvasCallback<Group[]> callback);

        @GET("/groups/{groupid}?include[]=permissions")
        void getDetailedGroup(@Path("groupid") long groupId, CanvasCallback<Group> callback);

        @GET("/groups/{groupid}/users")
        void getGroupUsers(@Path("groupid") long groupId, CanvasCallback<User[]> callback);

        @GET("/groups/{groupid}/users?include[]=avatar_url")
        void getGroupUsersWithAvatars(@Path("groupid") long groupId, CanvasCallback<User[]> callback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/users/self/groups")
        Group[] getGroupsSynchronous(@Query("page") int page);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static GroupsInterface buildInterface(CanvasCallback<?> callback) {
        return buildInterface(callback.getContext());
    }
    private static GroupsInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(GroupsInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageGroups(CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageGroupsCacheFilename());
        buildInterface(callback).getFirstPageGroups(callback);
    }

    public static void getAllGroups(final CanvasCallback<Group[]> callback){
        if(APIHelpers.paramIsNull(callback)) return;

        //Create a bridge.
        CanvasCallback<Group[]> bridge = new ExhaustiveGroupBridgeCallback(callback);

        //This should handle caching of ALL elements automatically.
        callback.readFromCache(getAllGroupsCacheFilename());
        getFirstPageGroups(bridge);
    }

    public static void getFirstPageGroupsInCourse(long courseID, CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageGroupsInCourseCacheFilename(courseID));
        buildInterface(callback).getFirstPageGroupsInCourse(courseID, callback);
    }

    public static void getAllGroupsInCourse(long courseID, CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        //Create a bridge.
        CanvasCallback<Group[]> bridge = new ExhaustiveGroupBridgeCallback(callback);


        callback.readFromCache(getAllGroupsInCourseCacheFilename(courseID));
        getFirstPageGroupsInCourse(courseID, bridge);
    }

    public static void getNextPageGroups(String nextURL, CanvasCallback<Group[]> callback) {
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageGroups(nextURL, callback);
    }

    public static void getDetailedGroup(long groupId, CanvasCallback<Group> callback) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getGroupCacheFilename(groupId));
        buildInterface(callback).getDetailedGroup(groupId,callback);
    }

    public static void getGroupUsers(long groupId, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(groupId, callback)) return;

        callback.readFromCache(getGroupUsersCacheFilename(groupId));
        buildInterface(callback).getGroupUsers(groupId,callback);
    }

    public static void getGroupUsersWithAvatars(long groupId, CanvasCallback<User[]> callback) {
        if (APIHelpers.paramIsNull(groupId, callback)) return;

        callback.readFromCache(getGroupUsersWithAvatarsCacheFilename(groupId));
        buildInterface(callback).getGroupUsersWithAvatars(groupId,callback);
    }

    /////////////////////////////////////////////////////////////////////////////
    // Helper Methods
    ////////////////////////////////////////////////////////////////////////////

    public static Map<Long, Group> createGroupMap(Group[] groups) {
        Map<Long, Group> groupMap = new HashMap<Long, Group>();
        for (Group group : groups) {
            groupMap.put(group.getId(), group);
        }
        return groupMap;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////

    public static Group[] getAllGroupsSynchronous(Context context) {
        try {
            ArrayList<Group> allGroups = new ArrayList<Group>();
            int page = 1;
            long firstItemId = -1;

            //for(ever) loop. break once we've run outta stuff;
            for(;;){
                Group[] groups = buildInterface(context).getGroupsSynchronous(page);
                page++;

                //This is all or nothing. We don't want partial data.
                if(groups == null){
                    return null;
                } else if(groups.length == 0){
                    break;
                } else if(groups[0].getId() == firstItemId){
                    break;
                } else{
                    firstItemId = groups[0].getId();
                    Collections.addAll(allGroups, groups);
                }
            }

            return allGroups.toArray(new Group[allGroups.size()]);

        } catch (Exception E) {
            return null;
        }
    }

}
