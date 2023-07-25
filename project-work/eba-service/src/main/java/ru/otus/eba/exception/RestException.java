package ru.otus.eba.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class RestException extends RuntimeException {

    public RestException(String message) {
        this(message, ExceptionCode.UNKNOWN);
    }

    public RestException(String message, Throwable cause) {
        this(message, ExceptionCode.UNKNOWN, cause);
    }

    public RestException(String message, ExceptionCode exceptionCode) {
        this(message, exceptionCode, (List<ErrorFieldData>) null);
    }

    public RestException(String message, ExceptionCode exceptionCode, Throwable cause) {
        this(message, exceptionCode, null, cause);
    }

    public RestException(String message, ExceptionCode exceptionCode, List<ErrorFieldData> fieldsErrors) {
        super(message);
        this.message = message;
        this.code = exceptionCode;
        this.fieldsErrors = fieldsErrors;
    }

    public RestException(String message, ExceptionCode exceptionCode, List<ErrorFieldData> fieldsErrors, Throwable cause) {
        super(message, cause);
        this.message = message;
        this.code = exceptionCode;
        this.fieldsErrors = fieldsErrors;
    }

    private final ExceptionCode code;
    private final String message;
    private final List<ErrorFieldData> fieldsErrors;
}
