package com.instructure.canvasapi.model;

import java.util.Date;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Author extends CanvasModel<Author> {

    // instance variables go here
    private long id;
    private String display_name;
    private String avatar_image_url;
    private String html_url;
    
    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return display_name;
    }

    public void setDisplayName(String display_name) {
        this.display_name = display_name;
    }

    public String getAvatarImageUrl() {
        return avatar_image_url;
    }

    public void setAvatarImageUrl(String avatar_image_url) {
        this.avatar_image_url = avatar_image_url;
    }

    public String getHtmlUrl() {
        return html_url;
    }

    public void setHtmlUrl(String html_url) {
        this.html_url = html_url;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getDisplayName();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public Author() {
    }

}