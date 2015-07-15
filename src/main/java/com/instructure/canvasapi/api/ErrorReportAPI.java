package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.ErrorReportResult;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;

import retrofit.RestAdapter;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class ErrorReportAPI {

    public interface ErrorReportInterface {
        @POST("/error_reports.json")
        void postErrorReport(@Query("error[subject]") String subject, @Query("error[url]") String url, @Query("error[email]") String email, @Query("error[comments]") String comments, @Query("error[user_perceived_severity") String userPerceivedSeverity, CanvasCallback<ErrorReportResult> callback);
    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static ErrorReportInterface buildInterface(CanvasCallback<?> callback) {
        //we don't want to use the normal buildAdapter method because the user might not always be logged in
        //when they use this method (like when they are on the login page) and the normal buildAdapter method prepends a
        // /api/v1 and requires a token.
        RestAdapter restAdapter = CanvasRestAdapter.buildTokenRestAdapter(callback.getContext());
        return restAdapter.create(ErrorReportInterface.class);
    }

    public static void postErrorReport(String subject, String url, String email, String comments, String userPerceivedSeverity, CanvasCallback<ErrorReportResult> callback) {
        if(APIHelpers.paramIsNull(callback, subject, url, email, comments, userPerceivedSeverity)) return;

        buildInterface(callback).postErrorReport(subject, url, email, comments, userPerceivedSeverity, callback);
    }
}
