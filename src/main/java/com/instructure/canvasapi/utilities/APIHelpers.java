package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.gson.Gson;
import com.instructure.canvasapi.model.User;
import retrofit.client.Header;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Joshua Dutton on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class APIHelpers {

    /**
     * SharedPreferences tags
     */
    private final static String SHARED_PREFERENCES_NAME = "canvas-kit-sp";
    private final static String SHARED_PREFERENCES_USER = "user";
    private final static String SHARED_PREFERENCES_MASQUERADED_USER = "masq-user";


    /**
     * Log Tag
     */
    public final static String LOG_TAG = "canvas-api";

    /**
     *
     * GetAssetsFile allows you to open a file that exists in the Assets directory.
     *
     * @param context
     * @param fileName
     * @return the contents of the file.
     */
    public static String getAssetsFile(Context context, String fileName) {
        try {
            String file = "";
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(fileName)));

            // do reading
            String line = "";
            while (line != null) {
                file += line;
                line = reader.readLine();
            }

            reader.close();
            return file;

        } catch (Exception e) {
            return "";
        }
    }

    /**
     * clearAllData is essentially a Logout.
     * Clears all data including credentials and cache.
     *
     * @param context
     * @return
     */
    public static boolean clearAllData(Context context) {
        if(context == null){
            return false;
        }

        //Clear credentials.
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();

        boolean sharedPreferencesDeleted =  editor.commit();

        //Delete cache.
        File cacheDir = new File(context.getFilesDir(), "cache");
        boolean cacheDeleted = FileUtilities.deleteAllFilesInDirectory(cacheDir);

        return sharedPreferencesDeleted && cacheDeleted;
    }


    /**
     * setCacheUser saves the currently signed in user to cache.
     * @param context
     * @param user
     * @return
     */

    public static boolean setCacheUser(Context context, User user) {
        if (user == null) {
            return false;
        } else {
            Gson gson = CanvasRestAdapter.getGSONParser();
            String userString = gson.toJson(user);
            if (userString == null) {
                return false;
            }
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String sharedPrefsKey = SHARED_PREFERENCES_USER;
            if(Masquerading.isMasquerading(context)){
                sharedPrefsKey = SHARED_PREFERENCES_MASQUERADED_USER;
            }

            editor.putString(sharedPrefsKey, userString);
            return  editor.commit();
        }
    }

    /**
     * setCachedAvatarURL is a helper to set a value on the cached user.
     * @param context
     * @param avatarURL
     * @return
     */
    public static boolean setCachedAvatarURL(Context context, String avatarURL){
        User user = getCacheUser(context);

        if(user == null){
            return false;
        }

        user.setAvatarURL(avatarURL);
        return setCacheUser(context, user);
    }

    /**
     * setCachedShortName is a helper to set a value on the cached user.
     * @param context
     * @param shortName
     * @return
     */
    public static boolean setCachedShortName(Context context, String shortName){
        User user = getCacheUser(context);

        if(user == null){
            return false;
        }

        user.setShortName(shortName);
        return setCacheUser(context, user);
    }

    /**
     * setCachedEmail is a helper to set a value on the cached user.
     * @param context
     * @param email
     * @return
     */

    public static boolean setCachedEmail(Context context, String email){
        User user = getCacheUser(context);

        if(user == null){
            return false;
        }

        user.setEmail(email);
        return setCacheUser(context, user);
    }

    /**
     * setCachedName is a helper to set a value on the cached user.
     * @param context
     * @param name
     * @return
     */

    public static boolean setCachedName(Context context, String name){
        User user = getCacheUser(context);

        if(user == null){
            return false;
        }

        user.setName(name);
        return setCacheUser(context, user);
    }


    /**
     * getCacheUser returns the signed-in user from cache. Returns null if there isn't one.
     * @param context
     * @return
     */

    public static User getCacheUser(Context context) {

        if(context == null){
            return null;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        String sharedPrefsKey = SHARED_PREFERENCES_USER;
        if(Masquerading.isMasquerading(context)){
            sharedPrefsKey = SHARED_PREFERENCES_MASQUERADED_USER;
        }

        String userString = sharedPreferences.getString(sharedPrefsKey, null);
        if (userString == null) {
            return null;
        } else {
            Gson gson = CanvasRestAdapter.getGSONParser();
            return gson.fromJson(userString, User.class);
        }
    }


    /**
     * getUserAgent returns the current user agent.
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {

        if(context == null){
            return "";
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("useragent", "");

    }

    /**
     * setUserAgent sets the user agent
     * @param context
     * @param userAgent
     * @return
     */
    public static boolean setUserAgent(Context context, String userAgent) {

        if(userAgent == null || userAgent.equals("")){
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("useragent", userAgent);
        return editor.commit();
    }

    /**
     * getFullDomain returns the protocol plus the domain.
     *
     * Returns "" if context is null or if the domain/token isn't set.
     * @return
     */
    public static String getFullDomain(Context context){
        String protocol = loadProtocol(context);
        String domain = getDomain(context);

        if (protocol == null || domain == null || protocol.equals("") || domain.equals("") ){
            return "";
        }

        return protocol + "://" + domain;
    }

    /**
     * getDomain returns the current domain. This function strips off all trailing / characters and the protocol.
     * @link APIHelpers.loadProtocol(context)
     * @param context
     * @return
     */
    public static String getDomain(Context context) {

        if(context == null){
            return "";
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String domain =  sharedPreferences.getString("domain", "");

        while (domain != null && domain.endsWith("/")) {
            domain = domain.substring(0, domain.length() - 1);
        }

        return domain;
    }

    /**
     * setDomain sets the current domain. It strips off the protocol.
     *
     * @param context
     * @param domain
     * @return
     */

    public static boolean setDomain(Context context, String domain) {


        if(domain == null || domain.equals("")){
            return false;
        }

        if (domain.contains("https://")) {
            domain = domain.substring(8);
        }
        if (domain.startsWith("http://")) {
            domain = domain.substring(7);
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("domain", domain);
        return editor.commit();
    }

    /**
     * getToken returns the OAuth token or "" if there isn't one.
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    /**
     * setToken sets the OAuth token
     * @param context
     * @param token
     * @return
     */
    public static boolean setToken(Context context, String token) {
        if(token == null || token.equals("")){
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        return editor.commit();
    }

    /**
     * loadProtocol returns the protocol or 'https' if there isn't one.
     * @param context
     * @return
     */
    public static String loadProtocol(Context context) {

        if(context == null){
            return "https";
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("api_protocol", "https");
    }

    /**
     * setProtocol sets the protocol
     * @param protocol
     * @param context
     * @return
     */
    public static boolean setProtocol(String protocol, Context context) {

        if(protocol == null || protocol.equals("")){
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("api_protocol", protocol);
        return editor.commit();
    }

    /**
     * Sets the default error delegate. This is the default if one isn't specified in the constructor
     *
     * @param errorDelegateClassName
     */
    public static void setDefaultErrorDelegateClass(Context context, String errorDelegateClassName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("errorDelegateClassName", errorDelegateClassName);
        editor.commit();
    }

    /**
     * Get the default error delegate.
     *
     * @param context
     */
    public static String getDefaultErrorDelegateClass(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("errorDelegateClassName", null);

    }
    /**
     * booleanToInt is a Helper function for Converting boolean to URL booleans (ints)
     */
    public static int booleanToInt(boolean bool) {
        if (bool) {
            return 1;
        }
        return 0;
    }

    /**
     * removeDomainFromUrl is a helper function for removing the domain from a url. Used for pagination/routing
     * @param url
     * @return
     */
    public static String removeDomainFromUrl(String url) {
        String prefix = "/api/v1/";
        int index = url.indexOf(prefix);
        if (index != -1) {
            url = url.substring(index + prefix.length());
        }
        return url;
    }


    /**
     * Helper methods for handling ISO 8601 strings of the following format:
     * "2008-03-01T13:00:00+01:00". It also supports parsing the "Z" timezone.
     */

    /**
     * Transform Calendar to ISO 8601 string.
     */
    public static String dateToString(final Date date) {
        if (date == null){
            return null;
        }

        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).format(date);
        return formatted.substring(0, 22) + ":" + formatted.substring(22);
    }

    /**
     * Transform ISO 8601 string to Calendar.
     */
    public static Date stringToDate(final String iso8601string) {
        try {
            String s = iso8601string.replace("Z", "+00:00");
            s = s.substring(0, 22) + s.substring(23);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US).parse(s);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * parseLinkHeaderResponse parses HTTP headers to return the first, next, prev, and last urls. Used for pagination.
     * @param context
     * @param headers
     * @return
     */
    public static LinkHeaders parseLinkHeaderResponse(Context context, List<Header> headers) {
        LinkHeaders linkHeaders = new LinkHeaders();

        for (int i = 0; i < headers.size(); i++) {
            if ("link".equalsIgnoreCase(headers.get(i).getName())) {
                String[] split = headers.get(i).getValue().split(",");
                for (int j = 0; j < split.length; j++) {
                    int index = split[j].indexOf(">");
                    String url = split[j].substring(0, index);
                    url = url.substring(1);

                    //Remove the domain.
                    url = removeDomainFromUrl(url);

                    if (split[j].contains("rel=\"next\"")) {
                        linkHeaders.nextURL = url;
                    } else if (split[j].contains("rel=\"prev\"")) {
                        linkHeaders.prevURL = url;
                    } else if (split[j].contains("rel=\"first\"")) {
                        linkHeaders.firstURL = url;
                    } else if (split[j].contains("rel=\"last\"")) {
                        linkHeaders.lastURL = url;
                    }
                }

                break;
            }
        }

        return linkHeaders;
    }

    public static APIStatusDelegate statusDelegateWithContext(final Context context) {
        return new APIStatusDelegate() {
            @Override
            public void onCallbackFinished() {

            }

            @Override
            public Context getContext() {
                return context;
            }
        };
    }

    /**
     * paramIsNull is a helper function for determining if callbacks/other objects are null;
     * @param callback
     * @param args
     * @return
     */
    public static boolean paramIsNull(CanvasCallback<?> callback, Object... args) {
        if (callback == null || callback.getContext() == null) {
            logParamsNull();
            return true;
        }

        for (Object arg : args) {
            if (arg == null) {
                logParamsNull();
                return true;
            }
        }
        return false;
    }

    /**
     * logParamsNull is a logging function helper
     */
    private static void logParamsNull() {
        Log.d(APIHelpers.LOG_TAG, "One or more parameters is null");
    }

}
