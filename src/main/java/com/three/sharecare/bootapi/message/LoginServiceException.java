package com.three.sharecare.bootapi.message;

public class LoginServiceException extends RuntimeException {

    public LoginServiceException() {
        super();
    }

    public LoginServiceException(String message) {
        super(message);
    }

    public LoginServiceException(Throwable cause) {
        super(cause);
    }

    public LoginServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
