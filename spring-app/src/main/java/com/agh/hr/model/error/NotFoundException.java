package com.agh.hr.model.error;


public class NotFoundException extends RuntimeException {
    public NotFoundException(String what) {
        super("Not found " + what);
    }
}
