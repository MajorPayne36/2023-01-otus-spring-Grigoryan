package ru.otus.test.exception;

public class CsvReaderException extends RuntimeException {
    public CsvReaderException(String message) {
        super(message);
    }
}
