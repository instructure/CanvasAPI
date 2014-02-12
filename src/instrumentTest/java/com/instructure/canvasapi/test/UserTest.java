package com.instructure.canvasapi.test;

import android.test.InstrumentationTestCase;
import com.google.gson.Gson;
import com.instructure.canvasapi.model.User;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

/**
 * Created by Josh Ruesch on 9/18/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UserTest extends InstrumentationTestCase {

    public void testUser() {
        String userJSON = "{\"id\":1111,\"name\":\"Joshua Ruesch\",\"short_name\":\"Josher\",\"sortable_name\":\"Ruesch, Joshua\",\"login_id\":\"login_id\",\"avatar_url\":\"https://www.example.com\",\"title\":null,\"bio\":null,\"primary_email\":\"primary_email\",\"time_zone\":\"America/Denver\",\"calendar\":{\"ics\":\"https://mobiledev.instructure.com/feeds/calendars/user_8JCkdINx6RO3dB8Ao5aPQCJO49p8XUpCbZgmqk7X.ics\"}}";

        Gson gson = CanvasRestAdapter.getGSONParser();
        User user = gson.fromJson(userJSON, User.class);

        assertEquals(user.getAvatarURL(), "https://www.example.com");

        assertEquals(user.getId(), 1111);

        assertEquals(user.getEmail(), "primary_email");

        assertEquals(user.getLoginId(), "login_id");

        assertEquals(user.getName(), "Joshua Ruesch");

        assertEquals(user.getShortName(),"Josher");
    }
}
