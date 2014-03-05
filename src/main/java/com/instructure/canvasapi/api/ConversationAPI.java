package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.Conversation;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.*;

import java.util.ArrayList;

/**
 * Created by Josh Ruesch on 8/9/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ConversationAPI {

    public enum ConversationScope { ALL,UNREAD,ARCHIVED,STARRED }

    private static String conversationScopeToString(ConversationScope scope){
        if(scope == ConversationScope.UNREAD) {
            return "unread";
        } else if (scope == ConversationScope.STARRED) {
            return "starred";
        } else if (scope == ConversationScope.ARCHIVED) {
            return "archived";
        }
        return "";
    }

    private static String getFirstPageConversationsCacheFilename(ConversationScope scope){
        return "/conversations/" + conversationScopeToString(scope);
    }

    private static String getDetailedConversationCacheFilename(long conversation_id){
        return "/conversations/" + conversation_id;
    }

    interface ConversationsInterface {
        @GET("/conversations/?interleave_submissions=1")
        void getFirstPageConversationList(@Query("scope") String scope, Callback<Conversation[]> callback);

        @GET("/conversations/?interleave_submissions=1")
        Conversation[] getFirstPageConversationList(@Query("scope") String scope);

        @GET("/{next}")
        void getNextPageConversationList(@EncodedPath("next") String nextURL, Callback<Conversation[]>callback);

        @GET("/conversations/{id}/?interleave_submissions=1")
        void getDetailedConversation(@Path("id") long conversation_id, @Query("auto_mark_as_read") int markAsRead, Callback<Conversation> callback);

        @POST("/conversations/{id}/add_message")
        void addMessageToConversation(@Path("id")long conversation_id, @Query("body")String message, CanvasCallback<Conversation> callback);

        @POST("/conversations?mode=sync")
        void createConversation(@EncodedQuery("recipients[]") String recipients, @Query("body") String message, @Query("group_conversation") int group, CanvasCallback<Response> callback);

        @DELETE("/conversations/{conversationid}")
        void deleteConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=unread")
        void markConversationAsUnread(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=archived")
        void archiveConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=read")
        void unArchiveConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);
    }

    private static ConversationsInterface buildInterface(CanvasCallback<?> callback) {
        return buildInterface(callback.getContext());
    }

    private static ConversationsInterface buildInterface(Context context) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context);
        return restAdapter.create(ConversationsInterface.class);
    }


    public static void getDetailedConversation(CanvasCallback<Conversation> callback, long conversation_id, boolean markAsRead) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getDetailedConversationCacheFilename(conversation_id));
        buildInterface(callback).getDetailedConversation(conversation_id, APIHelpers.booleanToInt(markAsRead), callback);
    }

    public static void getFirstPageConversations(CanvasCallback<Conversation[]> callback, ConversationScope scope) {
        if (APIHelpers.paramIsNull(callback)) return;

        callback.readFromCache(getFirstPageConversationsCacheFilename(scope));
        buildInterface(callback).getFirstPageConversationList(conversationScopeToString(scope), callback);
    }

    public static Conversation[] getFirstPageConversationsSynchronous(ConversationScope scope, Context context) {

       return buildInterface(context).getFirstPageConversationList(conversationScopeToString(scope));
    }

    public static void getNextPageConversations(CanvasCallback<Conversation[]> callback, String nextURL){
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildInterface(callback).getNextPageConversationList(nextURL, callback);
    }

    public static void addMessageToConversation(CanvasCallback<Conversation> callback, long conversation_id, String body){
        if (APIHelpers.paramIsNull(callback, body)) return;

        buildInterface(callback).addMessageToConversation(conversation_id, body, callback);
    }

    public static void createConversation(CanvasCallback<Response> callback, ArrayList<String> userIDs, String message, boolean groupBoolean){
        if(APIHelpers.paramIsNull(callback,userIDs,message)){return;}

        //The message has to be sent to somebody.
        if(userIDs.size() == 0){return;}

        //Manually build the recipients string.
        String recipientKey = "recipients[]";
        String recipientsParameter = userIDs.get(0);
        for(int i = 1; i < userIDs.size();i++)
        {
            recipientsParameter += "&"+recipientKey+"="+userIDs.get(i);
        }

        //Get the boolean parameter.
        int group = APIHelpers.booleanToInt(groupBoolean);


        buildInterface(callback).createConversation(recipientsParameter,message,group,callback);
    }

    public static void deleteConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(responseCanvasCallback).deleteConversation(conversationId,responseCanvasCallback);
    }

    public static void markConversationAsUnread(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(responseCanvasCallback).markConversationAsUnread(conversationId,responseCanvasCallback);
    }


    public static void archiveConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(responseCanvasCallback).archiveConversation(conversationId,responseCanvasCallback);
    }

    public static void unArchiveConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(responseCanvasCallback).unArchiveConversation(conversationId,responseCanvasCallback);
    }

}
