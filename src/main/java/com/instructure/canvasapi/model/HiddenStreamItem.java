package com.instructure.canvasapi.model;

import java.io.Serializable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class HiddenStreamItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean hidden;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    public boolean isHidden() {
        return hidden;
    }

}
