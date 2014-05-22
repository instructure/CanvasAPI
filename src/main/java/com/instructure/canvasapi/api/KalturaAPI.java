package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.KalturaConfig;
import com.instructure.canvasapi.model.KalturaSession;
import com.instructure.canvasapi.model.KalturaUploadToken;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.KalturaRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Nathan Button on 5/22/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class KalturaAPI {

    public interface KalturaConfigInterface{
        @GET("services/kaltura")
        void getKalturaConfigaration(Callback<KalturaConfig> callback);

        @POST("services/kaltura_session")
        void startKalturaSession(Callback<KalturaSession> callback);

    }
    public interface KalturaAPIInterface{

        @POST("index.php?service=uploadtoken&action=add")
        void getKalturaUploadToken(@Query("ks") String ks, Callback<KalturaUploadToken> callback);

        //Post to "Media_server/api_v3/index.php?service=uploadtoken&action=upload"
        //with ks=kaltura_session_id uploadTokenId= step3 Content-Disposition: form-data;
        //name="uploadTokenId"

        @POST("index.php?service=uploadtoken&action=upload")
        KalturaUploadToken uploadFileAtPath(@Query("ks")String ks, @Query("uploadTokenId") String uploadToken);

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

    public static void getKalturaUploadToken(final CanvasCallback<KalturaUploadToken> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        String kalturaToken = APIHelpers.getKalturaToken(callback.getContext());

        //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
        buildKalturaAPIInterface(callback).getKalturaUploadToken(kalturaToken, callback);
    }

    public static KalturaUploadToken uploadFileAtPath(String uploadToken, Context context) {

        try {


            String kalturaToken = APIHelpers.getKalturaToken(context);

            RestAdapter restAdapter = KalturaRestAdapter.buildAdapter(context);
            //callback.readFromCache(getAssignmentCacheFilename(courseID, assignmentID));
            return restAdapter.create(KalturaAPIInterface.class).uploadFileAtPath(kalturaToken, uploadToken);
        }  catch (Exception E){
            return null;
        }
    }

    //TODO: Create Method that links calls together to check if Kaltura is enabled and then get the sessionID then get an Upload ID
}
