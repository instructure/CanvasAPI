package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Brady Larson on 7/22/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class LockInfo extends CanvasComparable<LockInfo> implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<String> modulePrerequisiteNames = new ArrayList<String>();
    private String lockedModuleName;
    private LockedModule context_module;
    private String unlock_at;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////

    //During parsing, GSON will try. Which means sometimes we get 'empty' objects
    //They're non-null, but don't have any information.
    public boolean isEmpty(){
       return lockedModuleName == null
                && context_module == null
                && (modulePrerequisiteNames == null || modulePrerequisiteNames.size() == 0)
                && unlock_at == null;
    }

    public String getLockedModuleName() {
        if(context_module != null) {
            return context_module.getName();
        }
        else {
            return "";
        }
    }
    public void setLockedModuleName(String lockedModuleName) {
        this.lockedModuleName = lockedModuleName;
    }
    public ArrayList<String> getModulePrerequisiteNames() {
        return modulePrerequisiteNames;
    }

    public void setModulePrerequisiteNames(ArrayList<String> modulePrerequisiteNames) {
        this.modulePrerequisiteNames = modulePrerequisiteNames;
    }
    public Date getUnlockedAt() {
        if(context_module != null) {
            return context_module.getUnlock_at();
        } else if(unlock_at != null){
            return APIHelpers.stringToDate(unlock_at);
        } else {
            return null;
        }
    }



    public LockedModule getContext_module() {
        return context_module;
    }

    public void setContext_module(LockedModule context_module) {
        this.context_module = context_module;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getLockedModuleName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////

    public LockInfo() {}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.modulePrerequisiteNames);
        dest.writeString(this.lockedModuleName);
        dest.writeParcelable(this.context_module, flags);
        dest.writeString(this.unlock_at);
    }

    private LockInfo(Parcel in) {
        this.modulePrerequisiteNames = new ArrayList<String>();
        in.readList(this.getModulePrerequisiteNames(), String.class.getClassLoader());
        this.lockedModuleName = in.readString();
        this.context_module = in.readParcelable(LockedModule.class.getClassLoader());
        this.unlock_at = in.readString();
    }

    public static Creator<LockInfo> CREATOR = new Creator<LockInfo>() {
        public LockInfo createFromParcel(Parcel source) {
            return new LockInfo(source);
        }

        public LockInfo[] newArray(int size) {
            return new LockInfo[size];
        }
    };
}
