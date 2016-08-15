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

    public UserCallback(APIStatusDelegate statusDelegate) {
        super(statusDelegate);
    }

    public UserCallback(APIStatusDelegate statusDelegate, ErrorDelegate errorDelegate) {
        super(statusDelegate, errorDelegate);
    }

    public abstract void cachedUser(User user);
    public abstract void user(User user, Response response);

    @Override
    public void cache(User user, LinkHeaders linkHeaders, Response response) {
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

        statusDelegate.onCallbackFinished(SOURCE.API);

        try {
            APIHelpers.setCacheUser(getContext(), user);
        } catch (Exception E) {}

        user(user, response);
    }
}
