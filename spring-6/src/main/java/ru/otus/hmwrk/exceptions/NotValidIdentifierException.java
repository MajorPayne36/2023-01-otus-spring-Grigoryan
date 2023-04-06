package ru.otus.hmwrk.exceptions;

public class NotValidIdentifierException extends RuntimeException{
    public NotValidIdentifierException(Long id) {
        super("Genre id not valid: " + id);
    }
}
