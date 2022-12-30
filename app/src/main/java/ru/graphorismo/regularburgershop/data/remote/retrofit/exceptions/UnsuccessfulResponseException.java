package ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions;

public class UnsuccessfulResponseException extends RuntimeException{
    public UnsuccessfulResponseException(String message) {
        super(message);
    }
}
