package com.instructure.canvasapi.model;

import java.io.Serializable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UnreadConversationCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String unread_count;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getUnreadCount() {
        try {
            int count = Integer.parseInt(unread_count);
            if (count < 0) {
                count = 0;
            }

            return Integer.toString(count);
        }
        catch(Exception e) {
            return "0";
        }
    }
    public void setUnreadCount(int int_unread_count) {
        unread_count =  Integer.toString(int_unread_count);
    }
}
