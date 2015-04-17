package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Quiz;
import com.instructure.canvasapi.model.QuizQuestion;
import com.instructure.canvasapi.model.QuizSubmission;
import com.instructure.canvasapi.model.QuizSubmissionQuestionResponse;
import com.instructure.canvasapi.model.QuizSubmissionResponse;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

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

    public static String getFirstPageQuizQuestionsCacheFilename(CanvasContext canvasContext, long quizID) {
        return canvasContext.toAPIString() + "/quizzes/" + quizID + "/questions";
    }

    public static String getFirstPageQuizSubmissionsCacheFilename(CanvasContext canvasContext, long quizID) {
        return canvasContext.toAPIString() + "/quizzes/" + quizID + "/submissions";
    }

    public static String getFirstPageSubmissionQuestionsCacheFilename(long quizSubmissionID) {
        return "/quizSubmissions/" + quizSubmissionID + "/questions";
    }

    interface QuizzesInterface {
        @GET("/{context_id}/quizzes")
        void getFirstPageQuizzesList(@Path("context_id") long context_id, Callback<Quiz[]> callback);

        @GET("/{next}")
        void getNextPageQuizzesList(@Path(value = "next", encode = false) String nextURL, Callback<Quiz[]> callback);

        @GET("/{context_id}/quizzes/{quizid}")
        void getDetailedQuiz(@Path("context_id") long context_id, @Path("quizid") long quizid, Callback<Quiz> callback);

        @GET("/{next}")
        void getDetailedQuizFromURL(@Path(value = "next", encode = false) String quizURL, Callback<Quiz> callback);

        @GET("/{context_id}/quizzes/{quizid}/questions")
        void getFirstPageQuizQuestions(@Path("context_id") long context_id, @Path("quizid") long quizid, Callback<QuizQuestion[]> callback);

        @GET("/{next}")
        void getNextPageQuizQuestions(@Path(value = "next", encode = false) String nextURL, Callback<QuizQuestion[]> callback);

        @POST("/{context_id}/quizzes/{quizid}/submissions")
        void startQuiz(@Path("context_id") long context_id, @Path("quizid") long quizid, Callback<Response> callback);

        @GET("/{context_id}/quizzes/{quizid}/submissions")
        void getFirstPageQuizSubmissions(@Path("context_id") long context_id, @Path("quizid") long quizid, Callback<QuizSubmissionResponse> callback);

        @GET("/{next}")
        void getNextPageQuizSubmissions(@Path(value = "next", encode = false) String nextURL, Callback<QuizSubmissionResponse> callback);

        @GET("/quiz_submissions/{quiz_submission_id}/questions")
        void getFirstPageSubmissionQuestions(@Path("quiz_submission_id") long quizSubmissionId, Callback<QuizSubmissionQuestionResponse> callback);

        @GET("/{next}")
        void getNextPageSubmissionQuestions(@Path(value = "next", encode = false) String nextURL, Callback<QuizSubmissionQuestionResponse> callback);

        @POST("/quiz_submissions/{quiz_submission_id}/questions")
        void postQuizQuestionMultiChoice(@Path("quiz_submission_id") long quizSubmissionId, @Query("attempt") int attempt, @Query("validation_token") String token, @Query("quiz_questions[][id]") long questionId, @Query("quiz_questions[][answer]") long answer, Callback<QuizSubmissionQuestionResponse> callback);
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

    public static void getFirstPageQuizQuestions(CanvasContext canvasContext, long quiz_id, CanvasCallback<QuizQuestion[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageQuizQuestionsCacheFilename(canvasContext, quiz_id));
        buildInterface(callback, canvasContext).getFirstPageQuizQuestions(canvasContext.getId(), quiz_id, callback);
    }

    public static void getNextPageQuizQuestions(String nextURL, CanvasCallback<QuizQuestion[]> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageQuizQuestions(nextURL, callback);
    }

    public static void startQuiz(CanvasContext canvasContext, long quiz_id, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(callback, canvasContext).startQuiz(canvasContext.getId(), quiz_id, callback);
    }

    public static void getFirstPageQuizSubmissions(CanvasContext canvasContext, long quiz_id, CanvasCallback<QuizSubmissionResponse> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getFirstPageQuizSubmissionsCacheFilename(canvasContext, quiz_id));
        buildInterface(callback, canvasContext).getFirstPageQuizSubmissions(canvasContext.getId(), quiz_id, callback);
    }

    public static void getNextPageQuizSubmissions(String nextURL, CanvasCallback<QuizSubmissionResponse> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageQuizSubmissions(nextURL, callback);
    }

    public static void getFirstPageSubmissionQuestions(long quizSubmissionId, CanvasCallback<QuizSubmissionQuestionResponse> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getFirstPageSubmissionQuestionsCacheFilename(quizSubmissionId));
        buildInterface(callback, null).getFirstPageSubmissionQuestions(quizSubmissionId, callback);
    }

    public static void getNextPageSubmissionQuestions(String nextURL, CanvasCallback<QuizSubmissionQuestionResponse> callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildInterface(callback, null).getNextPageSubmissionQuestions(nextURL, callback);
    }

    public static void postQuizQuestionMultiChoice(QuizSubmission quizSubmission, long answerId, long questionId, CanvasCallback<QuizSubmissionQuestionResponse> callback){
        if (APIHelpers.paramIsNull(callback, quizSubmission, quizSubmission.getSubmissionId(), quizSubmission.getValidationToken())) { return; }

        buildInterface(callback, null).postQuizQuestionMultiChoice(quizSubmission.getId(), quizSubmission.getAttempt(), quizSubmission.getValidationToken(), questionId, answerId, callback);
    }
}
