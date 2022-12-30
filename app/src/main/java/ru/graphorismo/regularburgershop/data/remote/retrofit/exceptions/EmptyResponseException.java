package ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions;

public class EmptyResponseException extends RuntimeException{
    public EmptyResponseException(String message) {
        super(message);
    }
}
