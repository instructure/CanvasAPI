package com.instructure.canvasapi.utilities;

import com.instructure.canvasapi.model.CanvasModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A generic bridge to get around pagination when needed.
 *
 * Usage: Use this as your CanvasCallback and implement the ExhaustiveBridgeEvents callback.
 * In the ExhaustiveBridgeEvents callback add your 'next' api call until exhausted.
 * @param <T>
 */
public class ExhaustiveBridgeCallback<T extends CanvasModel> extends CanvasCallback<T[]>{

    private CanvasCallback<T[]> callback;
    private ExhaustiveBridgeEvents eventsCallback;
    private List<T> allItems = new ArrayList<T>();

    public interface ExhaustiveBridgeEvents {
        public void performApiCallWithExhaustiveCallback(CanvasCallback callback, String nextUrl);
        public Class classType();
    }

    public ExhaustiveBridgeCallback(CanvasCallback<T[]> callback, ExhaustiveBridgeEvents eventsCallback) {
        super(callback.statusDelegate);
        this.callback = callback;
        this.eventsCallback = eventsCallback;

        if(eventsCallback == null) {
            throw new UnsupportedOperationException("ExhaustiveBridgeEvents cannot be null");
        }
    }

    @Override
    public void cache(T[] ts) {
        //Do Nothing.
    }

    @Override
    public void firstPage(T[] ts, LinkHeaders linkHeaders, Response response) {
        String nextURL = linkHeaders.nextURL;
        Collections.addAll(allItems, ts);

        if(nextURL == null) {
            //Done
            if (allItems.size() > 0) {
                //Create an array of generics from our list.
                T[] toArray = (T[]) Array.newInstance(eventsCallback.classType(), allItems.size());
                for (int i = 0; i < allItems.size(); i++) {
                    toArray[i] = allItems.get(i);
                }

                callback.success(toArray, response);
            } else {
                //No items were retrieved.
                T[] toArray = (T[]) Array.newInstance(eventsCallback.classType(), allItems.size());
                callback.success(toArray, response);
            }
        } else {
            //Do more api calls
            eventsCallback.performApiCallWithExhaustiveCallback(this, nextURL);
        }
    }

    @Override
    public boolean onFailure(RetrofitError retrofitError) {
        return super.onFailure(retrofitError);
    }
}
