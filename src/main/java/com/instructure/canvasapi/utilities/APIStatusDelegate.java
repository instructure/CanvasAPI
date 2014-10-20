package com.instructure.canvasapi.utilities;

import android.content.Context;

/**
 * Created by Joshua Dutton on 1/9/14.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public interface APIStatusDelegate {
    public void onCallbackStarted();
    public void onCallbackFinished(CanvasCallback.SOURCE source);
    public void onNoNetwork();
    public Context getContext();
}
