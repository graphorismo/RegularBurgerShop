package ru.graphorismo.regularburgershop.data.remote.retrofit.exceptions;

public class NullNetworkResponseException extends RuntimeException{
    public NullNetworkResponseException(String message) {
        super(message);
    }
}
