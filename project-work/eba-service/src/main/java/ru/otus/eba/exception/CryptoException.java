package ru.otus.eba.exception;

public class CryptoException extends RuntimeException {

    public CryptoException(Exception e) {
        super(e);
    }

}
