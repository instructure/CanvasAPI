package com.instructure.canvasapi.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public final class IdlingResource  {

    public IdlingResource(String resourceName) {
    }

    public String getName() {
        return "";
    }

    public boolean isIdleNow() {
        return false;
    }

    public void registerIdleTransitionCallback() {
        //
    }

    public void incrementCounter(){
        //
    }

    public void decrementCounter(){
        //
    }

    private void notifyIdle() {
        //
    }
}