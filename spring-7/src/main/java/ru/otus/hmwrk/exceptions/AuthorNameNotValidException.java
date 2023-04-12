package ru.otus.hmwrk.exceptions;

public class AuthorNameNotValidException extends RuntimeException{
    public AuthorNameNotValidException(String name) {
        super("Author name not valid: " + name);
    }
}
