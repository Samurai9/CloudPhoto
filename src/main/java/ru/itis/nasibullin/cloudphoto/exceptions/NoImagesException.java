package ru.itis.nasibullin.cloudphoto.exceptions;

public class NoImagesException extends RuntimeException {
    public NoImagesException() {
        super();
    }

    public NoImagesException(String message) {
        super(message);
    }

    public NoImagesException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoImagesException(Throwable cause) {
        super(cause);
    }

    protected NoImagesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
