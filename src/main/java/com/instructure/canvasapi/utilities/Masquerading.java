package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.instructure.canvasapi.api.UserAPI;
import com.instructure.canvasapi.model.User;

import java.io.File;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class Masquerading {

    private final static String MASQ_PREF_NAME = "masquerading-SP";
    private final static String IS_MASQUERADING = "isMasquerading";
    private final static String MASQUERADE_ID = "masqueradeId";

    //function to add masquerading id to end of query string
    public static String addMasqueradeId(String url, Context context) {
        if(Masquerading.isMasquerading(context)) {
            long masqueradeId = getMasqueradingId(context);
            if(url.contains("?")) {
                url += "&as_user_id=" + masqueradeId;
            }
            else {
                url += "?as_user_id=" + masqueradeId;
            }
        }
        return url;
    }

    public static long getMasqueradingId(Context context){
        SharedPreferences settings = context.getSharedPreferences(MASQ_PREF_NAME, 0);
        return settings.getLong(MASQUERADE_ID,-1);
    }

    public static boolean isMasquerading(Context context) {
        SharedPreferences settings = context.getSharedPreferences(MASQ_PREF_NAME, 0);
        return settings.getBoolean(IS_MASQUERADING, false);
    }

    public static void stopMasquerading(Context context) {
        File cacheDir = new File(context.getFilesDir(), "cache");
        FileUtilities.deleteAllFilesInDirectory(cacheDir);

        CanvasRestAdapter.deleteHttpCache();

        SharedPreferences settings = context.getSharedPreferences(MASQ_PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_MASQUERADING, false);
        editor.putLong(MASQUERADE_ID, -1);
        editor.apply();
    }

    public static void startMasquerading(long masqueradeId, Context context, CanvasCallback<User> masqueradeUser, String domain) {

        File cacheDir = new File(context.getFilesDir(), "cache");
        FileUtilities.deleteAllFilesInDirectory(cacheDir);

        CanvasRestAdapter.deleteHttpCache();

        SharedPreferences settings = context.getSharedPreferences(MASQ_PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_MASQUERADING, true);
        editor.putLong(MASQUERADE_ID, masqueradeId);
        editor.apply();

        //Check to see if they're trying to switch domain as site admin
        if(domain != null && domain.trim().length() != 0) {
            APIHelpers.setDomain(context, domain);
        }

        UserAPI.getUserByIdNoCache(masqueradeId, masqueradeUser);
    }
}
