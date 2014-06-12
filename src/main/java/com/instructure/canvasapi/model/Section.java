package com.instructure.canvasapi.model;

import android.os.Parcel;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Josh Ruesch
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */

public class Section extends CanvasContext implements Comparable<CanvasContext> {

    public static final long serialVersionUID = 1L;

    private long id;
    private String name;

    long course_id ;
    String start_at;
    String end_at;

    private List<User> students = new ArrayList<User>();

    ///////////////////////////////////////////////////////////////////////////
    // Getters and Setters
    ///////////////////////////////////////////////////////////////////////////


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return Type.SECTION;
    }

    public long getCourse_id() {
        return course_id;
    }

    public Date getStart_at() {
        return APIHelpers.stringToDate(start_at);
    }

    public Date getEnd_at() {
        return APIHelpers.stringToDate(end_at);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getStudents() { return students; }

    public void setStudents(ArrayList<User> students) { this.students = students; }
    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public Date getComparisonDate() {
        return null;
    }

    @Override
    public String getComparisonString() {
        return getName();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.course_id);
        dest.writeString(this.start_at);
        dest.writeString(this.end_at);
        dest.writeList(this.students);
    }

    public Section() {
    }

    private Section(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.course_id = in.readLong();
        this.start_at = in.readString();
        this.end_at = in.readString();
        in.readList(this.students, User.class.getClassLoader());
    }

    public static Creator<Section> CREATOR = new Creator<Section>() {
        public Section createFromParcel(Parcel source) {
            return new Section(source);
        }

        public Section[] newArray(int size) {
            return new Section[size];
        }
    };
}
