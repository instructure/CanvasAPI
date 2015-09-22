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
 *
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class AvatarAPI extends BuildInterfaceAPI {

    interface AvatarsInterface{
        @GET("/users/self/avatars")
        void getFirstPageOfAvatarList( Callback<Avatar[]> callback);

        @PUT("/users/self")
        void updateAvatar(@Query("user[avatar][url]") String avatarURL, Callback<User> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageOfAvatarList(CanvasCallback<Avatar[]> callback){
        if(APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(AvatarsInterface.class, callback).getFirstPageOfAvatarList( callback);
        buildInterface(AvatarsInterface.class, callback).getFirstPageOfAvatarList( callback);
    }

    public static void updateAvatar(String avatarURL, CanvasCallback<User> callback){
        if(APIHelpers.paramIsNull(callback,avatarURL)){ return; }

        buildInterface(AvatarsInterface.class, callback).updateAvatar(avatarURL, callback);
    }

}
