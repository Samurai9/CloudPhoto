package ru.itis.nasibullin.cloudphoto.exceptions;

public class InvalidConfigException extends RuntimeException {
    public InvalidConfigException() {
        super();
    }

    public InvalidConfigException(String message) {
        super(message);
    }

    public InvalidConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidConfigException(Throwable cause) {
        super(cause);
    }

    protected InvalidConfigException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
