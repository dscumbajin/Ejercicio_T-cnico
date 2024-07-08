package com.example.clientes.exception;

public class ApiUnprocessableEntity extends RuntimeException {
    public ApiUnprocessableEntity(String message) {
        super(message);
    }
}
