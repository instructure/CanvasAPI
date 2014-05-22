package com.instructure.canvasapi.utilities;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.instructure.canvasapi.model.CanvasContext;
import com.mobprofs.retrofit.converters.SimpleXmlConverter;

import java.lang.reflect.Type;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Created by nbutton on 5/22/14.
 */
public class KalturaRestAdapter {
    /**
     * Returns a RestAdapter Instance that points at :domain/api/v1
     *
     * @param  callback A Canvas Callback
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(CanvasCallback callback) {
        callback.setFinished(false);
        return buildAdapter(callback.getContext());
    }

    /**
     * Returns a RestAdapter Instance
     *
     * @param  context An Android context.
     * @return A Canvas RestAdapterInstance. If setupInstance() hasn't been called, returns an invalid RestAdapter.
     */
    public static RestAdapter buildAdapter(final Context context) {

        if(context == null ){
            return null;
        }

        String domain = APIHelpers.getFullKalturaDomain(context);

        //Can make this check as we KNOW that the setter doesn't allow empty strings.
        if (domain == null || domain.equals("")) {
            Log.d(APIHelpers.LOG_TAG, "The RestAdapter hasn't been set up yet. Call setupInstance(context,token,domain)");
            return new RestAdapter.Builder().setServer("http://invalid.domain.com").build();
        }


        //Sets the auth token, user agent, and handles masquerading.
        return new RestAdapter.Builder()
                .setServer(domain + "/Media_server/api_v3//") // The base API endpoint.
                .setConverter(new SimpleXmlConverter())
                .build();


    }

    /**
     * Returns a RestAdapter Instance that points at :domain/
     *
     * Used ONLY in the login flow!
     *
     * @param  context An Android context.
     */
    public static RestAdapter buildTokenRestAdapter(final Context context){

        if(context == null ){
            return null;
        }

        String domain = APIHelpers.getFullKalturaDomain(context);

        return new RestAdapter.Builder()
                .setServer(domain) // The base API endpoint.
                .build();
    }


    /**
     * Returns a RestAdapter Instance that points at :domain/
     *
     * Used ONLY in the login flow!
     *
     */
    public static RestAdapter buildTokenRestAdapter(final String token, final String protocol, final String domain){

        if(token == null || protocol == null || domain == null ){
            return null;
        }



        return new RestAdapter.Builder()
                .setServer(protocol + "://" + domain) // The base API endpoint.
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade requestFacade) {
                        requestFacade.addHeader("Authorization", "Bearer " + token);
                    }
                })
                .build();
    }


//    /**
//     * Class that's used as to inject the user agent, token, and handles masquerading.
//     */
//
//    public static class CanvasRequestInterceptor implements RequestInterceptor{
//
//        Context context;
//
//        CanvasRequestInterceptor(Context context){
//            this.context = context;
//        }
//
//        @Override
//        public void intercept(RequestFacade requestFacade) {
//
//            final String token = APIHelpers.getToken(context);
//            final String userAgent = APIHelpers.getUserAgent(context);
//            final String domain = APIHelpers.loadProtocol(context) + "://" + APIHelpers.getDomain(context);
//
//            //Set the UserAgent
//            if(userAgent != null && !userAgent.equals(""))
//                requestFacade.addHeader("User-Agent", userAgent);
//
//            //Authenticate if possible
//            if(token != null && !token.equals("")){
//                requestFacade.addHeader("Authorization", "Bearer " + token);
//            }
//            //HTTP referer (originally a misspelling of referrer) is an HTTP header field that identifies the address of the webpage that linked to the resource being requested
//            //Source: https://en.wikipedia.org/wiki/HTTP_referer
//            //Some schools use an LTI tool called SlideShare that whitelists domains to be able to inject content into assignments
//            //They check the referrer in order to do this. 	203
//            requestFacade.addHeader("Referer", domain);
//
//            //Masquerade if necessary
//            if (Masquerading.isMasquerading(context)) {
//                requestFacade.addQueryParam("as_user_id", Long.toString(Masquerading.getMasqueradingId(context)));
//            }
//        }
//    }



    /**
     * Gets our custom GSON parser.
     *
     * @return Our custom GSON parser with custom deserializers.
     */

    public static Gson getGSONParser(){
        GsonBuilder b = new GsonBuilder();
        //TODO:Register custom parsers here!
        return b.create();
    }


    /**
     * Sets up the CanvasRestAdapter.
     *
     * Short hand for setdomain, setToken, and setProtocol.
     *
     * Clears out any old data before setting the new data.
     *
     * @param context An Android context.
     * @param ks_token An kaltura Token
     * @param domain The domain for the signed in user.
     *
     * @return Whether or not the instance was setup. Only returns false if the data is empty or invalid.
     */
    public static boolean setupInstance(Context context, String ks_token, String domain){
        if (ks_token == null ||
                ks_token.equals("") ||
                domain == null) {

            return false;
        }

        String protocol = "https";
        if(domain.startsWith("http://")) {
            protocol = "http";
        }
        return (APIHelpers.setKalturaDomain(context, domain) && APIHelpers.setKalturaToken(context, ks_token) && APIHelpers.setProtocol(protocol, context));
    }
}
