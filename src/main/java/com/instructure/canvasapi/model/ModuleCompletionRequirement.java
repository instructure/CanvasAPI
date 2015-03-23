package com.instructure.canvasapi.model;

import android.os.Parcel;
import com.google.gson.annotations.SerializedName;
import java.util.Date;

/**
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ModuleCompletionRequirement extends CanvasComparable<LockedModule>{

    private long id;
    private String type;

    @SerializedName("min_score")
    private double minScore;

    @SerializedName("max_score")
    private double maxScore;

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public double getMinScore() {
        return minScore;
    }
    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }
    public double getMaxScore() {
        return maxScore;
    }
    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
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
        return null;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Constructors
    ///////////////////////////////////////////////////////////////////////////
    public ModuleCompletionRequirement() {}

    private ModuleCompletionRequirement(Parcel in) {
        this.id = in.readLong();
        this.type = in.readString();
        this.minScore = in.readDouble();
        this.minScore = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.type);
        dest.writeDouble(this.minScore);
        dest.writeDouble(this.maxScore);
    }

    public static Creator<ModuleCompletionRequirement> CREATOR = new Creator<ModuleCompletionRequirement>() {
        public ModuleCompletionRequirement createFromParcel(Parcel source) {
            return new ModuleCompletionRequirement(source);
        }

        public ModuleCompletionRequirement[] newArray(int size) {
            return new ModuleCompletionRequirement[size];
        }
    };
}