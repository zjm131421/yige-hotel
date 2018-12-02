package com.yige.common.exception;

/**
 * @author zoujm
 * @since 2018/12/1 15:58
 */
public class GeneralException extends RuntimeException {

    private static final long serialVersionUID = 6403925731816439878L;

    public GeneralException() {
        super();
    }

    public GeneralException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(Throwable cause) {
        super(cause);
    }

}