package ru.otus.eba.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ExceptionCode {
    UNKNOWN,
    VALIDATION(HttpStatus.BAD_REQUEST),
    NOT_FOUND(HttpStatus.NOT_FOUND),
    FORBIDDEN(HttpStatus.FORBIDDEN);

    ExceptionCode() {
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    ExceptionCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Getter
    private final HttpStatus httpStatus;
}
