package com.toy.cache;

public class StatueException extends Exception {
    public StatueException() {
    }

    public StatueException(String message) {
        super(message);
    }

    public StatueException(String message, Throwable cause) {
        super(message, cause);
    }

    public StatueException(Throwable cause) {
        super(cause);
    }

    public StatueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
