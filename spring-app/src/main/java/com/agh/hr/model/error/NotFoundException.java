package com.agh.hr.model.error;


public class NotFoundException extends RuntimeException {

    public NotFoundException(String type, String id) {
        super("Not found " + type + " with id: " + id);
    }

    public NotFoundException(String type, Long id) {
        this(type, String.valueOf(id));
    }

}
