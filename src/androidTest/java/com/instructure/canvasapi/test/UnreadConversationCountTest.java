package com.instructure.canvasapi.test;

import android.test.InstrumentationTestCase;
import com.google.gson.Gson;
import com.instructure.canvasapi.model.UnreadConversationCount;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

/**
 * Created by Josh Ruesch on 9/18/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UnreadConversationCountTest extends InstrumentationTestCase {
    public void testUnreadConversationCount() {
        String unreadConversationCountJSON = "{\"unread_count\":\"3\"}";
        Gson gson = CanvasRestAdapter.getGSONParser();
        UnreadConversationCount unreadConversationCount = gson.fromJson(unreadConversationCountJSON, UnreadConversationCount.class);

        assertEquals(unreadConversationCount.getUnreadCount(), "3");
    }
}
