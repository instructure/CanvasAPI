package com.instructure.canvasapi.api;

import android.content.Context;
import com.instructure.canvasapi.model.Assignment;
import com.instructure.canvasapi.model.CanvasContext;
import com.instructure.canvasapi.model.ToDo;
import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.CanvasCallback;
import com.instructure.canvasapi.utilities.CanvasRestAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Path;
import retrofit.http.GET;

import java.util.*;

/**
 * Created by Brady Larson on 10/2/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ToDoAPI {
    private static String getUserTodosCacheFilename(){
        return "/users/self/todo";
    }

    private static String getCourseTodosCacheFilename(CanvasContext canvasContext){
        return canvasContext.toAPIString() + "/todo";
    }

    interface ToDosInterface {
        @GET("/users/self/todo")
        void getUserTodos(Callback<ToDo[]> callback);

        @GET("/{context_id}/todo")
        void getCourseTodos(@Path("context_id") long context_id, Callback<ToDo[]> callback);

        @DELETE("/{path}")
        void dismissTodo(@Path("path")String path, CanvasCallback<Response>responseCallback);

        /////////////////////////////////////////////////////////////////////////////
        // Synchronous
        /////////////////////////////////////////////////////////////////////////////

        @GET("/{context_id}/todo")
        ToDo[] getCourseTodos(@Path("context_id") long courseID);

        @GET("/users/self/todo")
        ToDo[] getUserTodos();

    }

    /////////////////////////////////////////////////////////////////////////
    // Build Interface Helpers
    /////////////////////////////////////////////////////////////////////////

    private static ToDosInterface buildInterface(CanvasCallback<?> callback, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(callback, canvasContext);
        return restAdapter.create(ToDosInterface.class);
    }

    private static ToDosInterface buildInterface(Context context, CanvasContext canvasContext) {
        RestAdapter restAdapter = CanvasRestAdapter.buildAdapter(context, canvasContext);
        return restAdapter.create(ToDosInterface.class);
    }

    /////////////////////////////////////////////////////////////////////////
    // API Calls
    /////////////////////////////////////////////////////////////////////////

    public static void getUserTodos(CanvasCallback<ToDo[]> callback) {
        if (APIHelpers.paramIsNull(callback)) { return; }

        callback.readFromCache(getUserTodosCacheFilename());
        buildInterface(callback, null).getUserTodos(callback);
    }

    public static void getCourseTodos(CanvasContext canvasContext, CanvasCallback<ToDo[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        callback.readFromCache(getCourseTodosCacheFilename(canvasContext));
        buildInterface(callback, canvasContext).getCourseTodos(canvasContext.getId(), callback);
    }

    public static void getTodos(CanvasContext canvasContext, final CanvasCallback<ToDo[]> callback) {
        if (APIHelpers.paramIsNull(callback, canvasContext)) { return; }

        if (canvasContext.getType() == CanvasContext.Type.USER) {
            getUserTodos(callback);
        } else {
            getCourseTodos(canvasContext, callback);
        }
    }

    public static void dismissTodo(Context context, ToDo toDo, CanvasCallback<Response> callback) {
        if (APIHelpers.paramIsNull(callback, toDo)) return;

        String path = APIHelpers.removeDomainFromUrl(toDo.getIgnore());
        buildInterface(callback, null).dismissTodo(path, callback);
    }


    /////////////////////////////////////////////////////////////////////////////
    // Helper Methods
    /////////////////////////////////////////////////////////////////////////////

    public static ArrayList<ToDo> mergeToDoUpcoming(ArrayList<ToDo> todos, ArrayList<ToDo> upcomingEvents) {
        if (todos == null && upcomingEvents == null) {
            return null;
        }

        if (todos == null) {
            todos = new ArrayList<ToDo>();
        }

        if (upcomingEvents == null) {
            upcomingEvents = new ArrayList<ToDo>();
        }

        //Add all Assignment ids from TODO
        HashMap<Long, Boolean> assignmentIds = new HashMap<Long, Boolean>();
        for (ToDo toDo : todos) {
            if (toDo.getAssignment() != null) {
                assignmentIds.put(toDo.getAssignment().getId(), true);
            }
        }
        //If the hashmap contains any assignment ids from Upcoming, it's a duplicate
        Iterator<ToDo> iterator = upcomingEvents.iterator();
        while (iterator.hasNext()) {
            ToDo current = iterator.next();
            Assignment assignment = current.getScheduleItem().getAssignment();
            if (assignment != null && assignmentIds.containsKey(assignment.getId())) {
                //We already have it in ToDo so remove the item.
                iterator.remove();
            }
        }

        int todoIndex = 0;
        int upcomingIndex = 0;
        ArrayList<ToDo> merged = new ArrayList<ToDo>();

        while (todoIndex < todos.size() || upcomingIndex < upcomingEvents.size()) {
            //We only have upcoming left.
            if (todoIndex >= todos.size()) {
                List<ToDo> subset = upcomingEvents.subList(upcomingIndex, upcomingEvents.size());
                for (ToDo upcomming : subset) {
                    merged.add(upcomming);
                }
                return merged;
            }

            //We only have todo left.
            if (upcomingIndex >= upcomingEvents.size()) {
                List<ToDo> subset = todos.subList(todoIndex, todos.size());
                for (ToDo td : subset) {
                    merged.add(td);
                }
                return merged;
            }

            //We need to determine which one comes sooner.
            Date toDoDate;
            if (todos.get(todoIndex).getAssignment() == null) {
                toDoDate = null;
            } else {
                toDoDate = todos.get(todoIndex).getAssignment().getDueDate();
            }
            Date upcomingDate = upcomingEvents.get(upcomingIndex).getScheduleItem().getStartDate();

            //handle null cases first
            if (toDoDate == null) {
                merged.add(upcomingEvents.get(upcomingIndex));
                upcomingIndex++;
            } else if (upcomingDate == null) {
                merged.add(todos.get(todoIndex));
                todoIndex++;
            } else if (toDoDate.before(upcomingDate)) {
                merged.add(todos.get(todoIndex));
                todoIndex++;
            } else {
                merged.add(upcomingEvents.get(upcomingIndex));
                upcomingIndex++;
            }
        }
        //Should never get here.
        return merged;
    }

    /////////////////////////////////////////////////////////////////////////////
    // Synchronous
    //
    // If Retrofit is unable to parse (no network for example) Synchronous calls
    // will throw a nullPointer exception. All synchronous calls need to be in a
    // try catch block.
    /////////////////////////////////////////////////////////////////////////////

    public static ToDo[] getTodosSynchronous(Context context, CanvasContext canvasContext) {

        if(context == null || canvasContext == null) {
            return null;
        }

        if (canvasContext.getType() == CanvasContext.Type.USER) {
            return getUserTodosSynchronous(context);
        } else {
            return getCourseTodosSynchronous(context, canvasContext);
        }
    }

    public static ToDo[] getUserTodosSynchronous(Context context) {
        if(context == null){
            return null;
        }

        //If not able to parse (no network for example), this will crash. Handle that case.
        try  {
            return buildInterface(context, null).getUserTodos();
        } catch (Exception E){
            return null;
        }

    }

    public static ToDo[] getCourseTodosSynchronous(Context context, CanvasContext canvasContext) {

        if(context == null || canvasContext == null) {
            return null;
        }

        //If not able to parse (no network for example), this will crash. Handle that case.
        try {
            return buildInterface(context, canvasContext).getCourseTodos(canvasContext.getId());
        } catch (Exception E){
            return null;
        }

    }

}
