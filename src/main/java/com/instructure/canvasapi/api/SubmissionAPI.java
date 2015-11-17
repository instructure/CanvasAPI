package com.instructure.canvasapi.api;

import android.content.Context;
import android.text.TextUtils;
import com.instructure.canvasapi.model.Attachment;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.FileUploadParams;
import com.instructure.canvasapi.model.LTITool;
import com.instructure.canvasapi.model.Page;
import com.instructure.canvasapi.model.RubricAssessment;
import com.instructure.canvasapi.model.RubricCriterionRating;
import com.instructure.canvasapi.model.StudentSubmission;
import com.instructure.canvasapi.model.Submission;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import com.instructure.canvasapi.utilities.ExhaustiveBridgeCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.*;
import retrofit.mime.TypedFile;

/**
 * Created by Brady Larson on 9/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class SubmissionAPI extends BuildInterfaceAPI {

    public interface SubmissionsInterface {
        @GET("/{context_id}/assignments/{assignmentID}/submissions?include[]=submission_comments")
        void getSubmissionsWithComments(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, Callback<Submission[]> callback);

        @GET("/{context_id}/assignments/{assignmentID}/submissions?include[]=submission_history")
        void getSubmissionsWithHistory(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, Callback<Submission[]> callback);

        @GET("/{context_id}/assignments/{assignmentID}/submissions?include[]=submission_comments&include[]=submission_history")
        void getSubmissionsWithCommentsAndHistory(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, Callback<Submission[]> callback);

        @GET("/{context_id}/assignments/{assignmentID}/submissions?include[]=submission_comments&include[]=submission_history&include[]=rubric_assessment&include[]=user")
        void getSubmissionsWithCommentsHistoryAndRubric(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, Callback<Submission[]> callback);

        @GET("/{context_id}/assignments/{assignmentID}/submissions/{submissionID}?include[]=rubric_assessment")
        void getSubmission(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Path("submissionID") long submissionID, Callback<Submission> callback);

        @GET("/{context_id}/assignments/{assignmentID}/submissions/{submissionID}?include[]=rubric_assessment&include[]=submission_comments&include[]=submission_history")
        void getSubmissionWithCommentsAndHistory(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Path("submissionID") long userID, Callback<Submission> callback);

        @GET("/{context_id}/students/submissions?include[]=assignment")
        void getSubmissionsForMultipleStudents(@Path("context_id") long context_id, @Query("student_ids[]") String ids, Callback<Submission[]> callback);

        @GET("/{context_id}/students/submissions?grouped=true&include[]=total_scores")
        void getSubmissionsAndGradesForMultipleStudents(@Path("context_id") long context_id, @Query("student_ids[]") String ids, Callback<StudentSubmission[]> callback);

        @GET("/{context_id}/students/submissions")
        void getSubmissions(@Path("context_id") long context_id, Callback<Submission[]> callback);

        @GET("/{next}")
        void getNextPageSubmissions(@Path(value = "next", encode = false) String nextURL, Callback<Submission[]> callback);

        @PUT("/{context_id}/assignments/{assignmentID}/submissions/{userID}")
        void postSubmissionComment(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Path("userID") long userID, @Query("comment[text_comment]") String comment, @Body String body, Callback<Submission> callback);

        @PUT("/{context_id}/assignments/{assignmentID}/submissions/{userID}")
        void postMediaSubmissionComment(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Path("userID") long userID, @Query("comment[media_comment_id]") String media_id,
                                        @Query("comment[media_comment_type]") String commentType, @Body String body, Callback<Submission> callback);
        @POST("/{context_id}/assignments/{assignmentID}/submissions")
        void postTextSubmission(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Query("submission[submission_type]") String submissionType, @Query("submission[body]") String text, @Body String body, Callback<Submission> callback);

        @POST("/{context_id}/assignments/{assignmentID}/submissions")
        void postURLSubmission(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Query("submission[submission_type]") String submissionType, @Query("submission[url]") String url, @Body String body, Callback<Submission> callback);

        @POST("/{context_id}/assignments/{assignmentID}/submissions")
        void postMediaSubmission(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Query("submission[submission_type]") String submissionType,
                                 @Query("submission[media_comment_id]") String kalturaId,@Query("submission[media_comment_type]") String mediaType, @Body String body, CanvasCallback<Submission> callback);

        @GET("/{path}")
        void getLTIFromAuthenticationURL(@Path(value = "path", encode = false) String url, Callback<LTITool> callback);

        @PUT("/{context_id}/assignments/{assignmentID}/submissions/{userID}")
        void postSubmissionRubricAssessmentMap(@Path("context_id") long context_id, @Path("assignmentID") long assignmentID, @Path("userID") long userID, @QueryMap Map<String, String> rubricAssessment, @Query("submission[posted_grade]") String assignmentScore, @Body String body, Callback<Submission> callback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////
        @POST("/courses/{courseId}/assignments/{assignmentId}/submissions/self/files")
        FileUploadParams getFileUploadParams(@Path("courseId") long courseId, @Path("assignmentId") long assignmentId, @Query("size") long size, @Query("name") String fileName, @Query("content_type") String content_type, @Body String body);

        @Multipart
        @POST("/")
        Attachment uploadCourseFile(@PartMap LinkedHashMap<String, String> params, @Part("file") TypedFile file, @Body String body);

        @POST("/courses/{courseId}/assignments/{assignmentID}/submissions")
        Submission postSubmissionAttachments(@Path("courseId") long courseId, @Path("assignmentID") long assignmentID, @Query("submission[submission_type]") String submissionType, @Query("submission[file_ids][]") ArrayList<String> attachments, @Body String body);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getSubmissionsWithComments(CanvasContext canvasContext, long assignmentID, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithComments(canvasContext.getId(), assignmentID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithComments(canvasContext.getId(), assignmentID, callback);
    }

    public static void getSubmissionsWithHistory(CanvasContext canvasContext, long assignmentID, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithHistory(canvasContext.getId(), assignmentID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithHistory(canvasContext.getId(), assignmentID, callback);
    }

    public static void getSubmissionsWithCommentsAndHistory(CanvasContext canvasContext, long assignmentID, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithCommentsAndHistory(canvasContext.getId(), assignmentID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithCommentsAndHistory(canvasContext.getId(), assignmentID, callback);
    }

    public static void getSubmissionsWithCommentsHistoryAndRubric(CanvasContext canvasContext, long assignmentID, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithCommentsHistoryAndRubric(canvasContext.getId(), assignmentID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsWithCommentsHistoryAndRubric(canvasContext.getId(), assignmentID, callback);
    }

    public static void getSubmission(CanvasContext canvasContext, long assignmentID, long userID, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmission(canvasContext.getId(), assignmentID, userID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmission(canvasContext.getId(), assignmentID, userID, callback);
    }

    public static void getSubmissions(CanvasContext canvasContext, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissions(canvasContext.getId(), callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissions(canvasContext.getId(), callback);
    }

    public static void getSubmissionsExhaustive(CanvasContext canvasContext, final CanvasCallback<Submission[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        CanvasCallback<Submission[]> bridge = new ExhaustiveBridgeCallback<>(Submission.class, callback, new ExhaustiveBridgeCallback.ExhaustiveBridgeEvents() {
            @Override
            public void performApiCallWithExhaustiveCallback(CanvasCallback callback, String nextUrl, boolean isCached) {
                SubmissionAPI.getNextPageSubmissionsChained(nextUrl, callback, isCached);
            }
        });

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissions(canvasContext.getId(), bridge);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissions(canvasContext.getId(), bridge);
    }

    public static void getNextPageSubmissions(String nextURL, CanvasCallback callback){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        buildCacheInterface(SubmissionsInterface.class, callback, null).getNextPageSubmissions(nextURL, callback);
        buildInterface(SubmissionsInterface.class, callback, null).getNextPageSubmissions(nextURL, callback);
    }

    public static void getNextPageSubmissionsChained(String nextURL, CanvasCallback callback, boolean isCache){
        if (APIHelpers.paramIsNull(callback, nextURL)) { return; }

        callback.setIsNextPage(true);
        if (isCache) {
            buildCacheInterface(SubmissionsInterface.class, callback, false).getNextPageSubmissions(nextURL, callback);
        } else {
            buildInterface(SubmissionsInterface.class, callback, false).getNextPageSubmissions(nextURL, callback);
        }
    }

    public static void getSubmissionWithCommentsAndHistoryChained(CanvasContext canvasContext, long assignmentID, long userID, final CanvasCallback<Submission> callback, boolean isCached) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        if (isCached) {
            buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionWithCommentsAndHistory(canvasContext.getId(), assignmentID, userID, callback);
        } else {
            buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionWithCommentsAndHistory(canvasContext.getId(), assignmentID, userID, callback);
        }
    }

    public static void getSubmissionWithCommentsAndHistory(CanvasContext canvasContext, long assignmentID, long userID, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionWithCommentsAndHistory(canvasContext.getId(), assignmentID, userID, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionWithCommentsAndHistory(canvasContext.getId(), assignmentID, userID, callback);
    }

    public static void postSubmissionComment(CanvasContext canvasContext, long assignmentID, long userID, String comment, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postSubmissionComment(canvasContext.getId(), assignmentID, userID, comment, "", callback);
    }
    public static void postMediaSubmissionComment(CanvasContext canvasContext, long assignmentID, long userID, String kalturaMediaId, String mediaType, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postMediaSubmissionComment(canvasContext.getId(), assignmentID, userID, kalturaMediaId, mediaType, "", callback);
    }

    public static void postTextSubmission(CanvasContext canvasContext, long assignmentID, String submissionType, String text, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, submissionType, text, canvasContext)) { return; }

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postTextSubmission(canvasContext.getId(), assignmentID, submissionType, text, "", callback);
    }

    public static void postURLSubmission(CanvasContext canvasContext, long assignmentID, String submissionType, String url, final CanvasCallback<Submission> callback) {
        if (APIHelpers.paramIsNull(callback, submissionType, url, canvasContext)) { return; }

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postURLSubmission(canvasContext.getId(), assignmentID, submissionType, url, "", callback);
    }

    public static void postMediaSubmission(CanvasContext canvasContext, long assignmentID, String submissionType, String kalturaId, String mediaType, final  CanvasCallback<Submission> callback){
        if (APIHelpers.paramIsNull(callback, submissionType, kalturaId, canvasContext)) { return; }

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postMediaSubmission(canvasContext.getId(), assignmentID, submissionType, kalturaId, mediaType, "", callback);
    }

    public static void getLTIFromAuthenticationURL(String url, final CanvasCallback<LTITool> callback) {
        if (APIHelpers.paramIsNull(callback, url)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, null).getLTIFromAuthenticationURL(url, callback);
        buildInterface(SubmissionsInterface.class, callback, null).getLTIFromAuthenticationURL(url, callback);
    }

    public static void getLTIFromAuthenticationURLChained(String url, final CanvasCallback<LTITool> callback, boolean isCached) {
        if (APIHelpers.paramIsNull(callback, url)) { return; }

        if (isCached) {
            buildCacheInterface(SubmissionsInterface.class, callback, null).getLTIFromAuthenticationURL(url, callback);
        } else {
            buildInterface(SubmissionsInterface.class, callback, null).getLTIFromAuthenticationURL(url, callback);
        }
    }

    /**
     *
     * @param canvasContext
     * @param callback
     * @param ids -- a list of comma separated ids or "all" if you want to get all available submissions
     */
    public static void getSubmissionsForMultipleStudents(CanvasContext canvasContext, CanvasCallback<Submission[]> callback, String ids) {
        if (APIHelpers.paramIsNull(callback, canvasContext, ids)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsForMultipleStudents(canvasContext.getId(), ids, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsForMultipleStudents(canvasContext.getId(), ids, callback);
    }

    /**
     *
     * @param canvasContext
     * @param callback
     * @param ids -- a list of comma separated ids or "all" if you want to get all available submissions
     */
    public static void getSubmissionsAndGradesForMultipleStudents(CanvasContext canvasContext, String ids, CanvasCallback<StudentSubmission[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext, ids)) { return; }

        buildCacheInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsAndGradesForMultipleStudents(canvasContext.getId(), ids, callback);
        buildInterface(SubmissionsInterface.class, callback, canvasContext).getSubmissionsAndGradesForMultipleStudents(canvasContext.getId(), ids, callback);
    }

    public static void postSubmissionRubricAssessmentMap(CanvasContext canvasContext, RubricAssessment rubricAssessment, String assignmentScore, long assignmentId, long userId, CanvasCallback<Submission> callback){
        if (APIHelpers.paramIsNull(canvasContext, rubricAssessment, callback)){return;}

        buildInterface(SubmissionsInterface.class, callback, canvasContext).postSubmissionRubricAssessmentMap(canvasContext.getId(), assignmentId, userId, generateRubricAssessmentQueryMap(rubricAssessment), assignmentScore, "", callback);
    }

    private static Map<String, String> generateRubricAssessmentQueryMap(RubricAssessment rubricAssessment){
        Map<String, String> map = new HashMap<String, String>();
        for (RubricCriterionRating entry : rubricAssessment.getRatings()) {
            map.put("rubric_assessment[" +entry.getCriterionId() +"][points]", String.valueOf(entry.getPoints()));
            if(entry.getComments() != null && !TextUtils.isEmpty(entry.getComments())){
                map.put("rubric_assessment[" +entry.getCriterionId() +"][comments]", entry.getComments());
            }
        }
        return map;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    /////////////////////////////////////////////////////////////////////////////
    public static FileUploadParams getFileUploadParams(Context context, long courseId, long assignmentId, String fileName, long size, String contentType){
        return buildInterface(SubmissionsInterface.class, context).getFileUploadParams(courseId, assignmentId, size, fileName, contentType, "");
    }

    public static Attachment uploadAssignmentSubmission(String uploadUrl, LinkedHashMap<String,String> uploadParams, String mimeType, File file){
        return buildUploadInterface(SubmissionsInterface.class, uploadUrl).uploadCourseFile(uploadParams, new TypedFile(mimeType, file), "");
    }

    public static Submission postSubmissionAttachments(Context context, long courseId, long assignmentId, ArrayList<String> attachments){
        return buildInterface(SubmissionsInterface.class, context).postSubmissionAttachments(courseId, assignmentId, "online_upload", attachments, "");
    }
}
