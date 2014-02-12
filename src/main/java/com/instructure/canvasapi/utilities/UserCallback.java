package com.instructure.canvasapi.utilities;

import com.instructure.canvasapi.model.User;
import retrofit.client.Response;

/**
 * Created by Josh Ruesch on 8/16/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

/**
 * Class used to automatically cache users.
 * Sets the cacheUser to the user returned by this class.
 */

public abstract class UserCallback extends CanvasCallback<User> {

    public UserCallback(APIStatusDelegate apiStatusDelegate) {
        super(apiStatusDelegate);
    }

    public UserCallback(APIStatusDelegate apiStatusDelegate, ErrorDelegate errorDelegate) {
        super(apiStatusDelegate, errorDelegate);
    }

    public abstract void cachedUser(User user);
    public abstract void user(User user, Response response);

    @Override
    public void readFromCache(String path) {
        cachedUser(APIHelpers.getCacheUser(getContext()));
    }

    @Override
    public void cache(User user) {
        cachedUser(user);
    }

    @Override
    public void firstPage(User user, LinkHeaders linkHeaders, Response response) {
        if (getContext() == null) return;
        user(user, response);
    }

    @Override
    public void success(User user, Response response) {

        // check if it's cancelled or detached
        if (getContext() == null) {
            return;
        }

        statusDelegate.onCallbackFinished();

        try {
            APIHelpers.setCacheUser(getContext(), user);
        } catch (Exception E) {}

        user(user, response);
    }
}
