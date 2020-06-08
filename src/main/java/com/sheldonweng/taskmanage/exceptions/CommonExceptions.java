package com.sheldonweng.taskmanage.exceptions;

/**
 * System common exceptions.
 *
 * @author Sheldon Weng
 */
public class CommonExceptions {

    public final static Integer SYSTEM_ERROR = 99999;

    /**
     * validate failed
     */
    public final static Integer VALIDATE_FAILED = 10001;

    /**
     * Entity is not found by id
     */
    public final static ServiceWarningException ENTITY_IS_NOT_FOUND_BY_ID =
        new ServiceWarningException(10002, "Entity is not found by id.");

    /**
     * User has not signed in.
     */
    public final static ServiceWarningException MEMBER_HAS_NOT_LOGINED_EXCEPTION =
        new ServiceWarningException(20001, "Please login first.");

    /**
     * Could not operate other user's data.
     */
    public final static ServiceWarningException OPERATION_FAILED =
        new ServiceWarningException(20002, "Could not operate other's data.");

}
