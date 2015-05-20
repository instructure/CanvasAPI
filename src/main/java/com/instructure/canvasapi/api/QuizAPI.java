package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.Quiz;
import com.instructure.canvasapi.model.QuizQuestion;
import com.instructure.canvasapi.model.QuizSubmission;
import com.instructure.canvasapi.model.QuizSubmissionQuestionResponse;
import com.instructure.canvasapi.model.QuizSubmissionResponse;
import com.instructure.canvasapi.model.QuizSubmissionTime;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class QuizAPI {

    private static final String QUIZ_SUBMISSION_SESSION_STARTED = "android_session_started";

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

        @PUT("/quiz_submissions/{quiz_submission_id}/questions/{question_id}/flag")
        void putFlagQuizQuestion(@Path("quiz_submission_id") long quizSubmissionId, @Path("question_id") long questionId, @Query("attempt") int attempt, @Query("validation_token") String token, CanvasCallback<Response> callback);

        @PUT("/quiz_submissions/{quiz_submission_id}/questions/{question_id}/unflag")
        void putUnflagQuizQuestion(@Path("quiz_submission_id") long quizSubmissionId, @Path("question_id") long questionId, @Query("attempt") int attempt, @Query("validation_token") String token, CanvasCallback<Response> callback);

        @POST("/quiz_submissions/{quiz_submission_id}/questions")
        void postQuizQuestionEssay(@Path("quiz_submission_id") long quizSubmissionId, @Query("attempt") int attempt, @Query("validation_token") String token, @Query("quiz_questions[][id]") long questionId, @Query("quiz_questions[][answer]") String answer, Callback<QuizSubmissionQuestionResponse> callback);

        @POST("/{context_id}/quizzes/{quiz_id}/submissions/{submission_id}/complete")
        void postQuizSubmit(@Path("context_id") long context_id, @Path("quiz_id") long quizId, @Path("submission_id") long submissionId, @Query("attempt") int attempt, @Query("validation_token") String token, Callback<QuizSubmissionResponse> callback);

        @POST("/{context_id}/quizzes/{quiz_id}/submissions/{submission_id}/events")
        void postQuizStartedEvent(@Path("context_id") long context_id, @Path("quiz_id") long quizId, @Path("submission_id") long submissionId, @Query("quiz_submission_events[][event_type]") String sessionStartedString, @Query("quiz_submission_events[][event_data][user_agent]") String userAgentString, CanvasCallback<Response> callback);

        @GET("/{context_id}/quizzes/{quiz_id}/submissions/{submission_id}/time")
        void getQuizSubmissionTime(@Path("context_id") long context_id, @Path("quiz_id") long quizId, @Path("submission_id") long submissionId, CanvasCallback<QuizSubmissionTime> callback);

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static QuizzesInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(QuizzesInterface.class);
    }

    private static QuizzesInterface buildInterfaceNoPerPage(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext, false);
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

    public static void putFlagQuizQuestion(QuizSubmission quizSubmission, long quizQuestionId, boolean shouldFlag, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, quizSubmission, quizSubmission.getSubmissionId(), quizSubmission.getValidationToken())) { return; }

        if(shouldFlag) {
            buildInterface(callback, null).putFlagQuizQuestion(quizSubmission.getId(), quizQuestionId, quizSubmission.getAttempt(), quizSubmission.getValidationToken(), callback);
        } else {
            buildInterface(callback, null).putUnflagQuizQuestion(quizSubmission.getId(), quizQuestionId, quizSubmission.getAttempt(), quizSubmission.getValidationToken(), callback);

        }
    }

    public static void postQuizQuestionEssay(QuizSubmission quizSubmission, String answer, long questionId, CanvasCallback<QuizSubmissionQuestionResponse> callback){
        if (APIHelpers.paramIsNull(callback, quizSubmission, quizSubmission.getSubmissionId(), quizSubmission.getValidationToken())) { return; }

        buildInterface(callback, null).postQuizQuestionEssay(quizSubmission.getId(), quizSubmission.getAttempt(), quizSubmission.getValidationToken(), questionId, answer, callback);
    }

    public static void postQuizSubmit(CanvasContext canvasContext, QuizSubmission quizSubmission, CanvasCallback<QuizSubmissionResponse> callback) {
        if (APIHelpers.paramIsNull(canvasContext, callback, quizSubmission, quizSubmission.getSubmissionId(), quizSubmission.getValidationToken())) { return; }

        buildInterface(callback, canvasContext).postQuizSubmit(canvasContext.getId(), quizSubmission.getQuizId(), quizSubmission.getId(), quizSubmission.getAttempt(), quizSubmission.getValidationToken(), callback);
    }

    public static void postQuizStartedEvent(CanvasContext canvasContext, QuizSubmission quizSubmission, String userAgentString, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(canvasContext, callback, quizSubmission, quizSubmission.getSubmissionId())) { return; }

        buildInterface(callback, canvasContext).postQuizStartedEvent(canvasContext.getId(), quizSubmission.getQuizId(), quizSubmission.getId(), QUIZ_SUBMISSION_SESSION_STARTED, userAgentString, callback);
    }

    public static void getQuizSubmissionTime(CanvasContext canvasContext, QuizSubmission quizSubmission, CanvasCallback<QuizSubmissionTime> callback) {
        if(APIHelpers.paramIsNull(canvasContext, callback, quizSubmission)) { return; }

        buildInterface(callback, canvasContext).getQuizSubmissionTime(canvasContext.getId(), quizSubmission.getQuizId(), quizSubmission.getId(), callback);
    }

}
