package com.instructure.canvasapi.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.KalturaConfig;
import com.instructure.canvasapi.model.KalturaSession;
import com.instructure.canvasapi.model.kaltura.xml;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.FileUtilities;
import com.instructure.canvasapi.utilities.KalturaRestAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.File;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Nathan Button on 5/22/14.
 * <p/>
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

//Make caching work
public class KalturaAPI {
    private static String getKalturaConfigCache() {
        return "/services/kaltura";
    }
    //Interface talking to Canvas servers
    public interface KalturaConfigInterface {
        @GET("/services/kaltura")
        void getKalturaConfigaration(Callback<KalturaConfig> callback);

        @POST("/services/kaltura_session")
        void startKalturaSession(Callback<KalturaSession> callback);

    }
    //Interface talking to Kaltura servers
    public interface KalturaAPIInterface {

        @POST("/index.php?service=uploadToken&action=add")
        void getKalturaUploadToken(@Query("ks") String ks, Callback<xml> callback);


        @POST("/index.php?service=media&action=addFromUploadedFile")
        xml getMediaIdForUploadedFileTokenSynchronous(@Query("ks") String ks, @Query("uploadTokenId") String uploadToken, @EncodedQuery("mediaEntry:name") String name, @EncodedQuery("mediaEntry:mediaType") String mediaType);

    }
    private static KalturaConfigInterface buildKalturaConfigInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(KalturaConfigInterface.class);
    }

    private static KalturaAPIInterface buildKalturaAPIInterface(CanvasCallback<?> callback) {
        RestAdapter restAdapter = KalturaRestAdapter.buildAdapter(callback);
        return restAdapter.create(KalturaAPIInterface.class);
    }

    public static void getKalturaConfigaration(final CanvasCallback<KalturaConfig> callback) {
        if (APIHelpers.paramIsNull(callback)) {
            return;
        }

        callback.readFromCache(getKalturaConfigCache());
        buildKalturaConfigInterface(callback, null).getKalturaConfigaration(callback);
    }

    public static void startKalturaSession(final CanvasCallback<KalturaSession> callback) {
        if (APIHelpers.paramIsNull(callback)) {
            return;
        }

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaConfigInterface(callback, null).startKalturaSession(callback);
    }

    public static void getKalturaUploadToken(final CanvasCallback<xml> callback) {
        if (APIHelpers.paramIsNull(callback)) {
            return;
        }

        String kalturaToken = APIHelpers.getKalturaToken(callback.getContext());

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaAPIInterface(callback).getKalturaUploadToken(kalturaToken, callback);
    }

    public static xml uploadFileAtPathSynchronous(String uploadToken, Uri fileUri, Context context) {

        if (context == null) {
            return null;
        }


        try {
            //Get needed vars
            String kalturaToken = APIHelpers.getKalturaToken(context);
            String baseUrl = APIHelpers.getFullKalturaDomain(context);
            File file = new File(fileUri.getPath());
            String fileType = FileUtilities.getMimeType(fileUri.getPath());
            String boundary = "---------------------------3klfenalksjflkjoi9auf89eshajsnl3kjnwal";

            //Build URL
            String fullUrl = baseUrl + "/api_v3/index.php?service=uploadtoken&action=upload";

            HttpPost post = new HttpPost(fullUrl);

            //Add info to body
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, null);
            entity.addPart("ks", new StringBody(kalturaToken));
            entity.addPart("uploadTokenId", new StringBody(uploadToken));
            ContentType contentType = ContentType.create(fileType);
            entity.addPart("fileData", new FileBody(file, contentType, file.getName()));
            post.setEntity(entity);

            //Send request
            HttpClient httpClient = new DefaultHttpClient();

            HttpResponse response = httpClient.execute(post);

            //Check that It worked.
            String stringResponse = EntityUtils.toString(response.getEntity());
            if(stringResponse.contains("error")){
                return null;
            }
            return getMediaIdForUploadedFileTokenSynchronous(context, kalturaToken, uploadToken, file.getName(), fileType);

        } catch (Exception e) {
            //Log.e(APIHelpers.LOG_TAG,E.getMessage());
            Log.e(APIHelpers.LOG_TAG, e.toString());
            return null;
        }

    }

    private static xml getMediaIdForUploadedFileTokenSynchronous(Context context, String ks, String uploadTocken, String fileName, String mimetype) {
        try {
            RestAdapter restAdapter = KalturaRestAdapter.buildAdapter(context);
            String mediaTypeConverted = FileUtilities.kalturaCodeFromMimeType(mimetype);
            return restAdapter.create(KalturaAPIInterface.class).getMediaIdForUploadedFileTokenSynchronous(ks, uploadTocken, fileName, mediaTypeConverted);
        } catch (Exception E) {
            Log.e(APIHelpers.LOG_TAG, E.getMessage());
            return null;
        }
    }

    //TODO: Create Method that links calls together to check if Kaltura is enabled and then get the sessionID then get an Upload ID
}
