package ru.itis.nasibullin.cloudphoto.exceptions;

public class NoImagesToUploadException extends RuntimeException {
    public NoImagesToUploadException() {
        super();
    }

    public NoImagesToUploadException(String message) {
        super(message);
    }

    public NoImagesToUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoImagesToUploadException(Throwable cause) {
        super(cause);
    }

    protected NoImagesToUploadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
