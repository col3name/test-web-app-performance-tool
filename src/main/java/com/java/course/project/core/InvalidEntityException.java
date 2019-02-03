package com.java.course.project.core;

public class IllegalAppParameterException extends Exception {
    public IllegalAppParameterException() {
    }

    public IllegalAppParameterException(String message) {
        super(message);
    }

    public IllegalAppParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalAppParameterException(Throwable cause) {
        super(cause);
    }

    public IllegalAppParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
