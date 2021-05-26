package com.agh.hr.model.error;

public class NotAuthorizedException extends RuntimeException {
    public NotAuthorizedException() {
        super("Not authorized");
    }
}
