package com.instructure.canvasapi.model;

import android.util.Log;

import com.instructure.canvasapi.utilities.APIHelpers;
import com.instructure.canvasapi.utilities.TestHelpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Brady Larson on 9/6/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public class LockedModule extends CanvasComparable<LockedModule> implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<ModuleName> prerequisites = new ArrayList<ModuleName>();
    private String unlock_at;
    private String name;
    private long context_id;

    public ArrayList<ModuleName> getPrerequisites() {
        return prerequisites;
    }

    public Date getUnlock_at() {
        return APIHelpers.stringToDate(unlock_at);
    }

    public String getName() {
        return name;
    }

    public long getContext_id() {
        return context_id;
    }

    private class ModuleName implements Serializable {
        private static final long serialVersionUID = 1L;

        private String getName() {
            return name;
        }

        private void setName(String name) {
            this.name = name;
        }

        private String name;
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
    // Unit Tests
    ///////////////////////////////////////////////////////////////////////////
    public static boolean isLockedModuleValid(LockedModule lockedModule) {
        if(lockedModule.getContext_id() <= 0) {
            Log.d(TestHelpers.UNIT_TEST_TAG, "Invalid LockedModule id");
            return false;
        }
        if(lockedModule.getName() == null) {
            Log.d(TestHelpers.UNIT_TEST_TAG, "Invalid LockedModule name");
            return false;
        }
        if(lockedModule.getUnlock_at() == null) {
            Log.d(TestHelpers.UNIT_TEST_TAG, "Invalid LockedModule unlock date");
            return false;
        }
        if(lockedModule.getPrerequisites() == null) {
            Log.d(TestHelpers.UNIT_TEST_TAG, "Invalid LockedModule prerequisites");
            return false;
        }
        for(int i = 0; i < lockedModule.getPrerequisites().size(); i++) {
            if(lockedModule.getPrerequisites().get(i).getName() == null) {
                Log.d(TestHelpers.UNIT_TEST_TAG, "Invalid LockedModule prereq name");
                return false;
            }
        }
        return true;
    }
}
