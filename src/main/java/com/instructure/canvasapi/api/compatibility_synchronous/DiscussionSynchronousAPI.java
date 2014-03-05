package com.instructure.canvasapi.api.compatibility_synchronous;

import android.content.Context;
import com.instructure.canvasapi.model.CanvasContext;

/**
 * Created by Josh Ruesch on 10/16/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class DiscussionSynchronousAPI {
    /**
     * markDiscussionReplyAsRead is a *SYNCHRONOUS* call that allows you to mark discussion topic headers as read.
     *
     * @param canvasContext
     * @param topicId
     * @param context
     * @return
     */
    public static boolean markDiscussionReplyAsRead(CanvasContext canvasContext, long topicId, Context context)
    {
        //PUT /api/v1/courses/:course_id/discussion_topics/:topic_id/read
        try
        {
            String putURL = "api/v1" + canvasContext.toAPIString();
            putURL = String.format(putURL + "/discussion_topics/%s/read", Long.toString(topicId));

            APIHttpResponse response = HttpHelpers.httpPut(putURL, context);
            return (response.responseCode == 204 && (response.responseBody == null || response.responseBody.length() == 0));
        }
        catch(Exception E)
        {
            return false;
        }
    }

    /**
     * markDiscussionEntryAsRead is a *SYNCHRONOUS* call that allows you to mark discussion topic entries (replies) as read.
     * @param canvasContext
     * @param topicId
     * @param entryId
     * @param context
     * @return
     */
    public static boolean markDiscussionEntryAsRead(CanvasContext canvasContext, long topicId, long entryId, Context context)
    {
        //PUT /api/v1/courses/:course_id/discussion_topics/:topic_id/entries/:entry_id/read
        try
        {
            String putURL = "api/v1" + canvasContext.toAPIString();
            putURL = String.format(putURL + "/discussion_topics/%s/entries/%s/read", Long.toString(topicId),Long.toString(entryId));

            APIHttpResponse response = HttpHelpers.httpPut(putURL, context);

            return (response.responseCode == 204 && (response.responseBody == null || response.responseBody.length() == 0));
        }
        catch(Exception E)
        {
            return false;
        }
    }
}
