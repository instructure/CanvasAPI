package com.instructure.canvasapi.model;

import com.instructure.canvasapi.utilities.APIHelpers;

import java.util.Date;

/**
 * @author Brady Larson
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class ModuleObject extends CanvasModel<ModuleObject> {

    /**
     * {
     // the unique identifier for the module
     id: 123,

     // the position of this module in the course (1-based)
     position: 2,


     // the name of this module
     name: "Imaginary Numbers and You",

     // (Optional) the date this module will unlock
     unlock_at: "2012-12-31T06:00:00-06:00",

     // Whether module items must be unlocked in order
     require_sequential_progress: true,

     // IDs of Modules that must be completed before this one is unlocked
     prerequisite_module_ids: [121, 122],

     // The state of this Module for the calling user
     // one of 'locked', 'unlocked', 'started', 'completed'
     // (Optional; present only if the caller is a student)
     state: 'started',

     // the date the calling user completed the module
     // (Optional; present only if the caller is a student)
     completed_at: nil
     }
     */

    private long id;
    private int position;
    private String name;
    private String unlock_at;
    private boolean require_sequential_progress;
    private long[] prerequisite_module_ids;
    private String state;
    private String completed_at;

    @Override
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getUnlock_at() {
        if(unlock_at != null) {
            return APIHelpers.stringToDate(unlock_at);
        }
        else {
            return null;
        }

    }
    public boolean isSequential_progress() {
        return require_sequential_progress;
    }
    public void setSequential_progress(boolean sequential_progress) {
        this.require_sequential_progress = sequential_progress;
    }
    public long[] getPrerequisite_ids() {
        return prerequisite_module_ids;
    }
    public void setPrerequisite_ids(long[] prerequisite_ids) {
        this.prerequisite_module_ids = prerequisite_ids;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Date getCompleted_at() {
        if(completed_at != null) {
            return APIHelpers.stringToDate(completed_at);
        }
        else {
            return null;
        }
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
        return getName();
    }

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModuleObject that = (ModuleObject) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}

