package com.instructure.canvasapi.api;

import com.instructure.canvasapi.model.CourseNickname;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class CourseNicknameAPI extends BuildInterfaceAPI {

    interface NicknameInterface {

        @GET("/users/self/course_nicknames/")
        void getAllNicknames(Callback<List<CourseNickname>> callback);

        @GET("/users/self/course_nicknames/{course_id}")
        void getNickname(@Path("course_id") long courseId, Callback<CourseNickname> callback);

        @PUT("/users/self/course_nicknames/{course_id}")
        void setNickname(@Path("course_id") long courseId, @Query("nickname") String nickname, @Body String body, Callback<CourseNickname> callback);

        @DELETE("/users/self/course_nicknames/{course_id}")
        void deleteNickname(@Path("course_id") long courseId, Callback<CourseNickname> callback);

        @DELETE("/users/self/course_nicknames/")
        void deleteAllNicknames(Callback<CourseNickname> callback);
    }

    public static void getAllNicknames(CanvasCallback<List<CourseNickname>> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(NicknameInterface.class, callback, false).getAllNicknames(callback);
    }

    public static void getNickname(long courseId, CanvasCallback<CourseNickname> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(NicknameInterface.class, callback, false).getNickname(courseId, callback);
    }

    public static void setNickname(long courseId, String nickname, CanvasCallback<CourseNickname> callback) {
        if (APIHelpers.paramIsNull(callback, nickname) || nickname.length() == 0) { return; }

        //Reduces the nickname to only 60 max chars per the api docs.
        nickname = nickname.substring(0, Math.min(nickname.length(), 60));

        buildInterface(NicknameInterface.class, callback, false).setNickname(courseId, nickname, "", callback);
    }

    public static void deleteNickname(long courseId, CanvasCallback<CourseNickname> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(NicknameInterface.class, callback, false).deleteNickname(courseId, callback);
    }

    public static void deleteAllNicknames(CanvasCallback<CourseNickname> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        buildInterface(NicknameInterface.class, callback, false).deleteAllNicknames(callback);
    }
}
