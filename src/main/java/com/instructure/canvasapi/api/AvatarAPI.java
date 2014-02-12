package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.Avatar;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Query;

/**
 * Created by Hoa Hoang on 10/15/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class AvatarAPI {

    interface AvatarsInterface{
        @GET("/users/self/avatars")
        void getFirstPageOfAvatarList( Callback<Avatar[]> callback);

        @PUT("/users/self")
        void updateAvatar(@Query("user[avatar][url]") String avatarURL, Callback<User> callback);
    }

    private static AvatarsInterface buildInterface(CanvasCallback<?> callback){
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback);
        return restAdapter.create(AvatarsInterface.class);
    }

    public static void getFirstPageOfAvatarList(CanvasCallback<Avatar[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(callback).getFirstPageOfAvatarList( callback);
    }

    public static void updateAvatar(String avatarURL, CanvasCallback<User> callback){
        if(APIHelpers.paramIsNull(callback,avatarURL)){ return; }

        buildInterface(callback).updateAvatar(avatarURL, callback);
    }

}
