package ru.otus.eba.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.otus.eba.dto.openapi.ErrorDto;
import ru.otus.eba.dto.openapi.ErrorFieldDto;
import ru.otus.eba.dto.openapi.StatusDto;
import ru.otus.eba.exception.ErrorFieldData;
import ru.otus.eba.exception.ExceptionCode;
import ru.otus.eba.exception.RestException;

import java.util.List;
import java.util.Objects;

/**
 * Глобальный обработчик исключений, возникший во время обработки запроса в контроллере.
 */
@Log4j2
@EnableWebMvc
@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDto> handleException(Exception e, HttpServletRequest request) {
        if (e instanceof BindException) {
            return handleException(getRestException((BindException) e), request);
        } else if (e instanceof HttpMessageNotReadableException) {
            return handleException(getRestException((HttpMessageNotReadableException) e), request);
        } else {
            String message = "Happened something wrong";
            if (!Objects.isNull(e)) {
                message = e.getMessage();
            }
            return handleException(new RestException(message, e), request);
        }
    }

    @ExceptionHandler({RestException.class})
    public ResponseEntity<ErrorDto> handleException(RestException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var status = new StatusDto();
        status.setMessage(e.getMessage());
        status.setCode(ExceptionCode.UNKNOWN.name());
        if (Objects.nonNull(e.getCode())) {
            status.setCode(e.getCode().name());
            httpStatus = e.getCode().getHttpStatus();
        }
        var dto = new ErrorDto();
        dto.setStatus(status);
        dto.setErrors(getErrorFieldDtoList(e));
        return ResponseEntity
                .status(httpStatus)
                .body(dto);
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleException(ResponseStatusException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);
        throw e;
    }

    private @Nullable List<ErrorFieldDto> getErrorFieldDtoList(RestException restException) {
        if (Objects.nonNull(restException.getFieldsErrors())) {
            return restException.getFieldsErrors().stream().map(item -> {
                var dto = new ErrorFieldDto();
                dto.setCode(item.getCode());
                dto.setName(item.getName());
                dto.setMessage(item.getMessage());
                return dto;
            }).toList();
        }
        return null;
    }

    private RestException getRestException(BindException bindException) {
        var fieldsErrors = bindException.getFieldErrors().stream()
                .map(objectError -> ErrorFieldData.builder()
                        .name(objectError.getField())
                        .code(objectError.getCode())
                        .message(objectError.getDefaultMessage())
                        .build())
                .toList();
        return new RestException(
                bindException.getMessage(),
                ExceptionCode.VALIDATION,
                fieldsErrors,
                bindException);
    }

    private RestException getRestException(HttpMessageNotReadableException notReadableException) {
        return new RestException(
                notReadableException.getMessage(),
                ExceptionCode.VALIDATION,
                notReadableException);
    }
}
