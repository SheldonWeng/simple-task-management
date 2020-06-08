package com.sheldonweng.taskmanage.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The TaskStatus enumeration.
 */
public enum TaskStatus {

    /**
     * Task is not started
     */
    TODO,

    /**
     * Doing task
     */
    DOING,

    /**
     * Task is pending
     */
    PENDING,

    /**
     * Task is completed
     */
    COMPLETED;

    @JsonIgnore
    public boolean couldBePaused() {
        return DOING.equals(this);
    }

}
