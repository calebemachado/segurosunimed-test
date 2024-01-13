package com.example.api.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String entity, String id) {
        super(String.format("Entity %s with id %s not found", entity, id));
    }
}
