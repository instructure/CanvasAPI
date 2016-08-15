package com.instructure.canvasapi.model;

/**
 * Created by Joshua Dutton on 11/20/13.
 *
 * Copyright (c) 2014 Instructure. All rights reserved.
 */
public abstract class CanvasModel<T extends CanvasComparable> extends CanvasComparable<T> {

    ///////////////////////////////////////////////////////////////////////////
    // Required Overrides
    ///////////////////////////////////////////////////////////////////////////

    public abstract long getId();

    ///////////////////////////////////////////////////////////////////////////
    // Overrides
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CanvasModel other = (CanvasModel) obj;
        if (getId() != other.getId())
            return false;
        return true;
    }
}
