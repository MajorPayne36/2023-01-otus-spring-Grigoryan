package ru.otus.eba.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorFieldData {
    private String name;
    private String code;
    private String message;
}
