package com.instructure.canvasapi.utilities;

/**
 * Created by hhoang on 2/24/16.
 */
public class EspressoIdlingUtils {

    private static final String RESOURCE = "RETROFIT";

    private static CanvasIdlingCounter mCountingIdlingResource =
            new CanvasIdlingCounter(RESOURCE);


    public static void incrementCounter(){
        mCountingIdlingResource.incrementCounter();
    }

    public static void decrementCounter(){
        mCountingIdlingResource.decrementCounter();
    }

    public static CanvasIdlingCounter getIdlingResource() {
        return mCountingIdlingResource;
    }
}
