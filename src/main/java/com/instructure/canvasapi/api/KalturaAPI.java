package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.KalturaConfig;
import com.instructure.canvasapi.model.KalturaSession;
import com.instructure.canvasapi.model.KalturaUploadToken;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Nathan Button on 5/22/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class KalturaAPI {

    public interface KalturaInterface{
        @GET("services/kaltura")
        void getKalturaConfigaration(Callback<KalturaConfig> callback);

        @POST("services/kaltura_session")
        void startKalturaSession(Callback<KalturaSession> callback);

        @POST("index.php?service=uploadtoken&action=add")
        void getKalturaUploadToken(@Query("ks") String ks, Callback<KalturaUploadToken> callback);

        //Post to "Media_server/api_v3/index.php?service=uploadtoken&action=upload"
        //with ks=kaltura_session_id uploadTokenId= step3 Content-Disposition: form-data;
        //name="uploadTokenId"

        @POST("index.php?service=uploadtoken&action=upload")
        void getKalturaUploadLocation(Callback<KalturaUploadToken> callback);

    }
}
