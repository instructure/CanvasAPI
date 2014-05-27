package com.instructure.canvasapi.api;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.instructure.canvasapi.api.compatibility_synchronous.APIHttpResponse;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.KalturaConfig;
import com.instructure.canvasapi.model.KalturaSession;
import com.instructure.canvasapi.model.kaltura.xml;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.KalturaRestAdapter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.net.URI;
import java.util.concurrent.Executor;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by Nathan Button on 5/22/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class KalturaAPI {

    public interface KalturaConfigInterface{
        @GET("/services/kaltura")
        void getKalturaConfigaration(Callback<KalturaConfig> callback);

        @POST("/services/kaltura_session")
        void startKalturaSession(Callback<KalturaSession> callback);

    }
    public interface KalturaAPIInterface{

        @POST("/index.php?service=uploadToken&action=add")
        void getKalturaUploadToken(@Query("ks") String ks, Callback<xml> callback);



        @POST("/index.php?service=uploadtoken&action=upload")
        xml uploadFileAtPathSynchronous(@Query("ks")String ks, @Query("uploadTokenId") String uploadToken);

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
        if (APIHelpers.paramIsNull(callback)) { return; }

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaConfigInterface(callback, null).getKalturaConfigaration(callback);
    }

    public static void startKalturaSession(final CanvasCallback<KalturaSession> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaConfigInterface(callback, null).startKalturaSession(callback);
    }

    public static void getKalturaUploadToken(final CanvasCallback<xml> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        String kalturaToken = APIHelpers.getKalturaToken(callback.getContext());

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaAPIInterface(callback).getKalturaUploadToken(kalturaToken, callback);
    }

    public static xml uploadFileAtPathSynchronous(String uploadToken,String fileType, Uri fileUri, Context context) {

        if(context == null)
        {
            return null;
        }

        try
        {
            Log.w("Nathan", "API");
            //TODO: Get needed vars
            String kalturaToken = APIHelpers.getKalturaToken(context);
            String baseUrl = APIHelpers.getFullKalturaDomain(context);
            File file = new File(fileUri.getPath());

            String boundary = "---------------------------This is the boundary";
            //TODO: Build URL
            String fullUrl= baseUrl+"/index.php?service=uploadtoken&action=upload&ks="+kalturaToken+"&uploadTokenId="+uploadToken;

            //TODO: Make HttpPost
            HttpPost post = new HttpPost(fullUrl);

            //TODO: Add info to body
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, null);
            entity.addPart("ks", new StringBody(kalturaToken));
            entity.addPart("uploadTokenId", new StringBody(uploadToken));
            entity.addPart("fileData", new FileBody(file, fileType));
            post.setEntity(entity);
            //TODO: Send request
            HttpClient httpClient = new DefaultHttpClient() ;
//I get a "org.apache.http.client.ClientProtocolException" here
            HttpResponse response = httpClient.execute(post);

            Log.d("Nathan", response.toString());

            //TODO: parse xml response

        } catch (Exception e){
            //Log.e(APIHelpers.LOG_TAG,E.getMessage());
            Log.e("Nathan", e.toString());
            e.printStackTrace();
            return null;
        }
            return null;

    }

    //TODO: Create Method that links calls together to check if Kaltura is enabled and then get the sessionID then get an Upload ID
}
