package com.instructure.canvasapi.api.compatibility_synchronous;

/**
 * Created by Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

import com.instructure.canvasapi.utilities.LinkHeaders;

/**
 * Our old way of doing LinkHeaders. Provided for synchronous APIs.
 * @see com.instructure.canvasapi.utilities.LinkHeaders
 */
public class APIHttpResponse
{	
	public String responseBody;

	public int responseCode;

	//used for pagination
	public LinkHeaders linkHeaders;
	@Deprecated
	public String prevURL;
	@Deprecated
	public String nextURL;
	@Deprecated
	public String lastURL;
	@Deprecated
	public String firstURL;
}