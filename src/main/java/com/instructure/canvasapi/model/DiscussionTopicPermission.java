package com.instructure.canvasapi.model;

import java.io.Serializable;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class DiscussionTopicPermission implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean attach = false;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

	public boolean canAttach() {
		return attach;
	}
	public void setCanAttach(boolean can_attach) {
		this.attach = can_attach;
	}
}
