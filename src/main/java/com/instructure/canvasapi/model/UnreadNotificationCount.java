package com.instructure.canvasapi.model;

import java.io.Serializable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class UnreadNotificationCount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private int count;
    private int unread_count;
    private String notification_category;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUnreadCount() {
        return unread_count;
    }

    public void setUnreadCount(int unread_count) {
        this.unread_count = unread_count;
    }

    public String getNotificationCategory() {
        return notification_category;
    }

    public void setNotificationCategory(String notification_category) {
        this.notification_category = notification_category;
    }

}

