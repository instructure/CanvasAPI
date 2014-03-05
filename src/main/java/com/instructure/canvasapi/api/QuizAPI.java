package com.instructure.canvasapi.api;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Quiz;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.EncodedPath;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class QuizAPI {

    public static String getFirstPageQuizzesCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/quizzes";
    }

    public static String getDetailedQuizCacheFilename(CanvasContext canvasContext, long quizID){
        return canvasContext.toAPIString() + "/quizzes/" + quizID;
    }



    interface QuizzesInterface {
        @GET("/{context_id}/quizzes")
        void getFirstPageQuizzesList(@Path("context_id") long context_id, Callback<Quiz[]> callback);

        @GET("/{next}")
        void getNextPageQuizzesList(@EncodedPath("next") String nextURL, Callback<Quiz[]> callback);

        @GET("/{context_id}/quizzes/{quizid}")
        void getDetailedQuiz(@Path("context_id") long context_id, @Path("quizid") long quizid, Callback<Quiz> callback);

        @GET("/{next}")
        void getDetailedQuizFromURL(@EncodedPath("next") String quizURL, Callback<Quiz> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static QuizzesInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(QuizzesInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getFirstPageQuizzes(CanvasContext canvasContext, CanvasCallback<Quiz[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageQuizzesCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getFirstPageQuizzesList(canvasContext.getId(), callback);
    }

    public static void getNextPageQuizzes(String nextURL, CanvasCallback<Quiz[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageQuizzesList(nextURL, callback);
    }

    public static void getDetailedQuiz(CanvasContext canvasContext, long quiz_id, CanvasCallback<Quiz> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getDetailedQuizCacheFilename(canvasContext,quiz_id));
        buildInterface(callback, canvasContext).getDetailedQuiz(canvasContext.getId(), quiz_id, callback);
    }

    public static void getDetailedQuizFromURL(String url, CanvasCallback<Quiz> callback) {
        if (APIHelpers.paramIsNull(callback,url)) { return; }

        callback.readFromCache(url);
        buildInterface(callback, null).getDetailedQuizFromURL(url,callback);
    }
}
