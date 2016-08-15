package com.instructure.canvasapi.api.compatibility_synchronous;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Josh Ruesch on 10/16/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class VimeoSynchronousAPI {
    /**
     * getVimeoThumbnail is a method used to get a thumbnail url for a given vimeo video id.
     * @param vimeoId
     * @param context
     * @return
     */
    public static String getVimeoThumbnail(String vimeoId, Context context) {
        try {
            String url = "http://vimeo.com/api/v2/video/" + vimeoId + ".json";

            String response = HttpHelpers.externalHttpGet(context, url).responseBody;
            JSONArray a = new JSONArray(response);
            JSONObject json = a.getJSONObject(0);
            if (json.has("thumbnail_small"))
                return json.getString("thumbnail_small");
            else
                return json.getString("thumbnail");
        } catch (Exception E) {
            return "default_video_poster.png";
        }
    }
}
