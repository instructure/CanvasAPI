package com.instructure.canvasapi.utilities;

import android.content.Context;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public interface APIStatusDelegate {
    void onCallbackStarted();
    void onCallbackFinished(CanvasCallback.SOURCE source);
    void onNoNetwork();
    Context getContext();
}
