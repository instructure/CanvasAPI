package com.instructure.canvasapi.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * Created by Joshua Dutton on 9/6/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Tab extends CanvasModel<Tab> {

    public static final String TYPE_INTERNAL = "internal";
    public static final String TYPE_EXTERNAL = "external";

    // id constants (these should never change in the API)
    public static final String SYLLABUS_ID = "syllabus";
    public static final String AGENDA_ID = "agenda";
    public static final String ASSIGNMENTS_ID = "assignments";
    public static final String DISCUSSIONS_ID = "discussions";
    public static final String PAGES_ID = "pages";
    public static final String PEOPLE_ID = "people";
    public static final String QUIZZES_ID = "quizzes";
    public static final String FILES_ID = "files";
    public static final String ANNOUNCEMENTS_ID = "announcements";
    public static final String MODULES_ID = "modules";
    public static final String GRADES_ID = "grades";
    public static final String COLLABORATIONS_ID = "collaborations";
    public static final String CONFERENCES_ID = "conferences";
    public static final String OUTCOMES_ID = "outcomes";
    public static final String NOTIFICATIONS_ID = "notifications";
    public static final String HOME_ID = "home";
    public static final String CHAT_ID = "chat";
    public static final String SETTINGS_ID = "settings";

    // API Variables
    private String id;
    private String label;
    private String type;
    private String html_url;    // internal url
    private String full_url;         // external url

    @SerializedName("url")
    private String LTI_url;

    ///////////////////////////////////////////////////////////////////////////
    // Getters & Setters
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public long getId() {
        return 0;
    }

    @Override
    public String getComparisonString() {
        return null;
    }

    @Override
    public Date getComparisonDate() {
        return null;
    }

    public String getTabId() {
        return id;
    }
    public String getLabel() {
        return label;
    }
    public String getType() {
        return type;
    }
    public boolean isExternal() {
        return type.equals(TYPE_EXTERNAL);
    }
    public String getUrl(Context context) {
        String temp_html_url = html_url;

        //Domain strips off trailing slashes.
        if(!temp_html_url.startsWith("/")){
            temp_html_url = "/" + temp_html_url;
        }

        return APIHelpers.getDomain(context) + temp_html_url;
    }

    public String getExternalUrl() {
        return full_url;
    }

    public String getLTIUrl() {
        return LTI_url;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    private Tab () {}

    public static Tab newInstance(String id, String label) {
        Tab result = new Tab();
        result.id = id;
        result.label = label;
        result.type = TYPE_INTERNAL;
        return result;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////


    @Override
    public String toString(){
        if(this.getTabId() == null || this.getLabel() == null){
            return "";
        }

        return this.getTabId()+":"+this.getLabel();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tab tab = (Tab) o;

        if (!id.equals(tab.id)) return false;
        if (!label.equals(tab.label)) return false;

        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.label);
        dest.writeString(this.type);
        dest.writeString(this.html_url);
        dest.writeString(this.full_url);
    }

    private Tab(Parcel in) {
        this.id = in.readString();
        this.label = in.readString();
        this.type = in.readString();
        this.html_url = in.readString();
        this.full_url = in.readString();
    }

    public static Parcelable.Creator<Tab> CREATOR = new Parcelable.Creator<Tab>() {
        public Tab createFromParcel(Parcel source) {
            return new Tab(source);
        }

        public Tab[] newArray(int size) {
            return new Tab[size];
        }
    };
}
