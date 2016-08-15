package com.instructure.canvasapi.utilities;

public class RetrofitCounter {
    private static MockCounter counter = new MockCounter();

    public static void increment(){
        counter.increment();
    }

    public static void decrement() {
        counter.decrement();
    }
}
