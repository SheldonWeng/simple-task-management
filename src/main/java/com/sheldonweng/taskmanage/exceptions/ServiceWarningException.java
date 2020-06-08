package com.sheldonweng.taskmanage.exceptions;

/**
 * Service Warning Exception
 *
 * @author Sheldon Weng
 */
public class ServiceWarningException extends RuntimeException {

    private static final long serialVersionUID = 3905631373620076994L;

    protected Integer errorCode = -1;
    protected String errorMessage;

    public ServiceWarningException() {
        super();
    }

    public ServiceWarningException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public ServiceWarningException(String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorMessage = errorMessage;
    }

    public ServiceWarningException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
