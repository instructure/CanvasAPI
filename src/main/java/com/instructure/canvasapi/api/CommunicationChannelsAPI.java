package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.CommunicationChannel;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class CommunicationChannelsAPI extends BuildInterfaceAPI {

    public interface CommunicationChannelInterface {

        @GET("/users/{user_id}/communication_channels")
        void getCommunicationChannels(@Path("user_id") long userId, CanvasCallback<CommunicationChannel[]> callback);
    }
    public static void getCommunicationChannels(final long userId, final CanvasCallback<CommunicationChannel[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildCacheInterface(CommunicationChannelInterface.class, callback, null).getCommunicationChannels(userId, callback);
        buildInterface(CommunicationChannelInterface.class, callback, null).getCommunicationChannels(userId, callback);
    }
}
