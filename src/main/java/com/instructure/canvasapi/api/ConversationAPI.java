package com.instructure.canvasapi.api;

import android.content.Context;

import com.instructure.canvasapi.model.Conversation;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.EncodedQuery;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Copyright (c) 2015 Instructure. All rights reserved.
 */
public class ConversationAPI extends BuildInterfaceAPI {

    public enum ConversationScope { ALL,UNREAD,ARCHIVED,STARRED,SENT }
    private static String conversationScopeToString(ConversationScope scope){
        if(scope == ConversationScope.UNREAD) {
            return "unread";
        } else if (scope == ConversationScope.STARRED) {
            return "starred";
        } else if (scope == ConversationScope.ARCHIVED) {
            return "archived";
        } else if (scope == ConversationScope.SENT) {
            return "sent";
        }
        return "";
    }

    public enum WorkflowState {READ,UNREAD,ARCHIVED}

    private static String conversationStateToString(WorkflowState state){
        if(state == WorkflowState.UNREAD) {
            return "unread";
        } else if (state == WorkflowState.READ) {
            return "read";
        } else if (state == WorkflowState.ARCHIVED) {
            return "archived";
        }
        return "";
    }

    interface ConversationsInterface {
        @GET("/conversations/?interleave_submissions=1")
        void getFirstPageConversationList(@Query("scope") String scope, Callback<Conversation[]> callback);


        @GET("/{next}")
        void getNextPageConversationList(@Path(value = "next", encode = false) String nextURL, Callback<Conversation[]>callback);

        @GET("/conversations/{id}/?interleave_submissions=1")
        void getDetailedConversation(@Path("id") long conversation_id, @Query("auto_mark_as_read") int markAsRead, Callback<Conversation> callback);

        @POST("/conversations/{id}/add_message")
        void addMessageToConversation(@Path("id")long conversation_id, @Body TypedInput message, CanvasCallback<Conversation> callback);

        @POST("/conversations?group_conversation=true")
        void createConversation(@EncodedQuery("recipients[]") String recipients, @Body TypedInput message, @Query("subject") String subject, @Query("context_code") String contextCode, @Query("bulk_message") int isGroup, CanvasCallback<Response> callback);

        @DELETE("/conversations/{conversationid}")
        void deleteConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=unread")
        void markConversationAsUnread(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=archived")
        void archiveConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}?conversation[workflow_state]=read")
        void unArchiveConversation(@Path("conversationid")long conversationID, CanvasCallback<Response>responseCallback);

        @PUT("/conversations/{conversationid}")
        void setIsStarred(@Path("conversationid")long conversationID, @Query("conversation[starred]") boolean isStarred, CanvasCallback<Conversation>responseCallback);

        @PUT("/conversations/{conversationid}")
        void setIsSubscribed(@Path("conversationid")long conversationID, @Query("conversation[subscribed]") boolean isSubscribed, CanvasCallback<Conversation>responseCallback);

        @PUT("/conversations/{conversationid}")
        void setSubject(@Path("conversationid")long conversationID, @Query("conversation[subject]") String subject, CanvasCallback<Conversation>responseCallback);

        @PUT("/conversations/{conversationid}")
        void setWorkflowState(@Path("conversationid")long conversationID, @Query("conversation[workflow_state]") String workflowState, CanvasCallback<Conversation>responseCallback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/conversations/?interleave_submissions=1")
        Conversation[] getFirstPageConversationList(@Query("scope") String scope, @Query("per_page") int number);

        @GET("/conversations/{id}/?interleave_submissions=1")
        Conversation getDetailedConversationSynchronous(@Path("id") long conversation_id);

        @POST("/conversations/{id}/add_message")
        Conversation addMessageToConversationSynchronous(@Path("id")long conversation_id, @Query("body")String message, @Query("attachment_ids[]") List<String> attachments);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getDetailedConversation(CanvasCallback<Conversation> callback, long conversation_id, boolean markAsRead) {
        if (APIHelpers.paramIsNull(callback)) return;

        buildCacheInterface(ConversationsInterface.class, callback).getDetailedConversation(conversation_id, APIHelpers.booleanToInt(markAsRead), callback);
        buildInterface(ConversationsInterface.class, callback).getDetailedConversation(conversation_id, APIHelpers.booleanToInt(markAsRead), callback);
    }

    public static void getFirstPageConversations(CanvasCallback<Conversation[]> callback, ConversationScope scope) {
        if (APIHelpers.paramIsNull(callback)) return;

        buildCacheInterface(ConversationsInterface.class, callback).getFirstPageConversationList(conversationScopeToString(scope), callback);
        buildInterface(ConversationsInterface.class, callback).getFirstPageConversationList(conversationScopeToString(scope), callback);
    }


    public static void getNextPageConversations(CanvasCallback<Conversation[]> callback, String nextURL){
        if (APIHelpers.paramIsNull(callback, nextURL)) return;

        callback.setIsNextPage(true);
        buildCacheInterface(ConversationsInterface.class, callback, false).getNextPageConversationList(nextURL, callback);
        buildInterface(ConversationsInterface.class, callback, false).getNextPageConversationList(nextURL, callback);
    }

    public static void addMessageToConversation(CanvasCallback<Conversation> callback, long conversation_id, String message){
        TypedInput typedInput = createTypedInput(message);
        if (APIHelpers.paramIsNull(callback, typedInput)) return;

        buildInterface(ConversationsInterface.class, callback, false).addMessageToConversation(conversation_id, typedInput, callback);
    }

    public static void createConversation(CanvasCallback<Response> callback, ArrayList<String> userIDs, String message, boolean isGroup, String contextId){
        createConversation(callback, userIDs, message, "", contextId, isGroup);
    }

    public static void createConversation(CanvasCallback<Response> callback, ArrayList<String> userIDs, String message, String subject, String contextId, boolean isGroup){
        TypedInput typedInput = createTypedInput(message);
        if(APIHelpers.paramIsNull(callback,userIDs, typedInput)){return;}

        //The message has to be sent to somebody.
        if(userIDs.size() == 0){return;}

        //Manually build the recipients string.
        String recipientKey = "recipients[]";
        String recipientsParameter = userIDs.get(0);
        for(int i = 1; i < userIDs.size();i++){
            recipientsParameter += "&"+recipientKey+"="+userIDs.get(i);
        }

        buildInterface(ConversationsInterface.class, callback, false).createConversation(recipientsParameter, typedInput, subject, contextId, isGroup ? 0 : 1, callback);
    }

    public static void deleteConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).deleteConversation(conversationId, responseCanvasCallback);
    }

    public static void markConversationAsUnread(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).markConversationAsUnread(conversationId, responseCanvasCallback);
    }


    public static void archiveConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).archiveConversation(conversationId, responseCanvasCallback);
    }

    public static void unArchiveConversation(CanvasCallback<Response>responseCanvasCallback, long conversationId){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).unArchiveConversation(conversationId, responseCanvasCallback);
    }

    public static void subscribeToConversation(long conversationId, boolean isSubscribed, CanvasCallback<Conversation>responseCanvasCallback){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).setIsSubscribed(conversationId, isSubscribed, responseCanvasCallback);
    }

    public static void starConversation(long conversationId, boolean isStarred, CanvasCallback<Conversation>responseCanvasCallback){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).setIsStarred(conversationId, isStarred, responseCanvasCallback);
    }

    public static void setConversationSubject(long conversationId, String newSubject, CanvasCallback<Conversation>responseCanvasCallback){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).setSubject(conversationId, newSubject, responseCanvasCallback);
    }

    public static void setConversationWorkflowState(long conversationId, WorkflowState workflowState, CanvasCallback<Conversation>responseCanvasCallback){
        if(APIHelpers.paramIsNull(responseCanvasCallback)){return;}

        buildInterface(ConversationsInterface.class, responseCanvasCallback, false).setWorkflowState(conversationId, conversationStateToString(workflowState), responseCanvasCallback);
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////

    public static Conversation[] getFirstPageConversationsSynchronous(ConversationScope scope, Context context, int numberToReturn) {
        try{
            return buildInterface(ConversationsInterface.class, context).getFirstPageConversationList(conversationScopeToString(scope), numberToReturn);
        } catch (Exception E){
            return null;
        }
    }

    public static Conversation getDetailedConversationSynchronous(Context context, long conversationId){
        try {
            return buildInterface(ConversationsInterface.class, context).getDetailedConversationSynchronous(conversationId);
        } catch (Exception E){
            return null;
        }
    }

    public static Conversation addMessageToConversationSynchronous(Context context, long conversationId, String messageBody, List<String> attachmentIds){
        if (APIHelpers.paramIsNull(context, attachmentIds, messageBody)){return null;}

        return buildInterface(ConversationsInterface.class, context, false).addMessageToConversationSynchronous(conversationId, messageBody, attachmentIds);
    }

    private static TypedInput createTypedInput(String message){
        //In order to form the expected post body we must create a json string
        JSONObject json = new JSONObject();
        try {
            json.put("body", message);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Use this json string to create a TypedByteArray using UTF-8
        TypedInput in = null;
        try {
            in = new TypedByteArray("application/json", json.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return in;
    }
}
