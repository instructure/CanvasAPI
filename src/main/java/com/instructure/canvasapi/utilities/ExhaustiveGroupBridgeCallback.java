package com.instructure.canvasapi.utilities;

import com.instructure.canvasapi.api.GroupAPI;
import com.instructure.canvasapi.model.Group;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Josh Ruesch on 3/4/14.
 */

/*
    This class is used as a 'bridge' to get all the pages of the group api.
    It'll grab all pages from the group api and then return the ENTIRE results to the passed in callback.
 */

public class ExhaustiveGroupBridgeCallback extends CanvasCallback<Group[]> {

    //This is the callback that will get results once ALL the groups have been returned.
    CanvasCallback<Group[]> finalGroupCallback;
    ArrayList<Group> allGroups = new ArrayList<Group>();

    public ExhaustiveGroupBridgeCallback(APIStatusDelegate apiStatusDelegate, CanvasCallback<Group[]> finalCourseCallback){
        super(apiStatusDelegate);
        this.finalGroupCallback = finalCourseCallback;
    }


    @Override
    public void cache(Group[] groups) {}

    @Override
    public void firstPage(Group[] groups, LinkHeaders linkHeaders, Response response) {
        String nextURL = linkHeaders.nextURL;
        Collections.addAll(allGroups, groups);

        if(nextURL == null){
            Group[] allGroupsArray = new Group[allGroups.size()];
            allGroupsArray = allGroups.toArray(allGroupsArray);

            finalGroupCallback.success(allGroupsArray, response);
        } else{
            GroupAPI.getNextPageGroups(nextURL, this);
        }
    }

    @Override
    public void failure(RetrofitError retrofitError){
        finalGroupCallback.failure(retrofitError);
    }
}
