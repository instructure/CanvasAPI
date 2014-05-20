package com.instructure.canvasapi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Joshua Dutton on 7/5/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public abstract class CanvasContext extends CanvasModel<CanvasContext> implements Serializable, Parcelable{

    public static final String HOME_FEED = "feed";
    public static final String HOME_WIKI = "wiki";
    public static final String HOME_MODULES = "modules";
    public static final String HOME_ASSIGNMENTS = "assignments";
    public static final String HOME_SYLLABUS = "syllabus";

    public static enum Type {
        GROUP, COURSE, USER, UNKNOWN;
        public static boolean isGroup(CanvasContext canvasContext) {
            if(canvasContext == null){
                return false;
            }
            return canvasContext.getType() == GROUP;
        }
        public static boolean isCourse(CanvasContext canvasContext) {
            if(canvasContext == null){
                return false;
            }
            return canvasContext.getType() == COURSE;
        }
        public static boolean isUser(CanvasContext canvasContext) {
            if(canvasContext == null){
                return false;
            }
            return canvasContext.getType() == USER;
        }
        public static boolean isUnknown(CanvasContext canvasContext) {
            if(canvasContext == null){
                return false;
            }
            return canvasContext.getType() == UNKNOWN;
        }
    };
    
    public abstract String getName();
    public abstract Type getType();
    public abstract long getId();

    protected String default_view;

    protected CanvasContextPermission permissions;

    public class CanvasContextPermission implements Serializable {
        boolean create_discussion_topic;
    }

    public void setPermissions(CanvasContextPermission permissions) {
        this.permissions = permissions;
    }

    public CanvasContextPermission getPermissions(){
        return permissions;
    }

    public boolean canCreateDiscussion(){
        return (permissions != null && permissions.create_discussion_topic);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

    /**
     *  Make sure they have the same type and the same ID.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CanvasContext that = (CanvasContext) o;

        if (getType()!= that.getType() || getId() != that.getId()) return false;

        return true;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Helpers
    ///////////////////////////////////////////////////////////////////////////

    /**
     * For courses, returns the course code.
     * For everything else, returns the Name;
     */

    public String getSecondaryName(){
        String secondaryName = getName();
        if(getType() == CanvasContext.Type.COURSE){
            secondaryName = ((Course)this).getCourseCode();
        }
        return secondaryName;
    }


    /**
     *  Used for Cache Filenames in the API.
     */
    public String toAPIString(){
        String typeString;
        if(getType().equals(Type.GROUP)){
            typeString = "groups";
        } else if (getType().equals(Type.COURSE)){
            typeString = "courses";
        } else{
            typeString = "users";
        }

        String idString = Long.toString(getId());
        if (getType() == Type.USER && getId() == 0) {
            idString = "self";
        }

        return "/" + typeString + "/" + idString;
    }

    /**
     * @returns group_:id or course_:id
     */
    public String getContextId(){

        String prefix = "";
        if(getType() == Type.COURSE){
            prefix = "course";
        } else if (getType() == Type.GROUP){
            prefix = "group";
        }

        return prefix + "_" + getId();
    }

    /**
     * Get home page label returns the fragment identifier.
     * @return
     */
    public String getHomePageID() {
        if (default_view == null) {
            //notifications can't be hidden, so if for some reason we don't have the home page
            //send them to notifications instead
            return Tab.NOTIFICATIONS_ID;
        }
        if (default_view.equals(HOME_FEED)) {
            return Tab.NOTIFICATIONS_ID;
        }
        if (default_view.equals(HOME_SYLLABUS)) {
            return Tab.SYLLABUS_ID;
        }
        if (default_view.equals(HOME_WIKI)) {
            return Tab.PAGES_ID;
        }
        if (default_view.equals(HOME_ASSIGNMENTS)) {
            return Tab.ASSIGNMENTS_ID;
        }
        if (default_view.equals(HOME_MODULES)) {
            return Tab.MODULES_ID;
        }
        return Tab.NOTIFICATIONS_ID; //send them to notifications if we don't know what to do
    }

    public static CanvasContext getGenericContext(final Type type, final long id, final String name){
        CanvasContext canvasContext = new CanvasContext() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public Type getType() {
                return type;
            }

            @Override
            public long getId() {
                return id;
            }

            @Override
            public int compareTo(CanvasContext canvasContext) {
                return 0;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeSerializable(type);
                parcel.writeLong(id);
                parcel.writeString(name);

            }
        };

        return canvasContext;
    }

    public static CanvasContext emptyCourseContext() {
        return getGenericContext(Type.COURSE, 0, "");
    }

    public static CanvasContext emptyUserContext() {
        return getGenericContext(Type.USER, 0, "");
    }


}
