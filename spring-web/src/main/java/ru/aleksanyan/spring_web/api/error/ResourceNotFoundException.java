package ru.aleksanyan.spring_web.api.error;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String msg){ super(msg); }
}