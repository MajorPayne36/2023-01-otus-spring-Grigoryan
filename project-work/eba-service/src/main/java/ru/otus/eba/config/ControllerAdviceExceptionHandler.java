package ru.otus.eba.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.otus.eba.dto.openapi.ErrorDto;
import ru.otus.eba.dto.openapi.ErrorFieldDto;
import ru.otus.eba.dto.openapi.StatusDto;
import ru.otus.eba.exception.ExceptionCode;
import ru.otus.eba.exception.RestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

/**
 * Глобальный обработчик исключений, возникший во время обработки запроса в контроллере.
 */
@Log4j2
@EnableWebMvc
@ControllerAdvice
public class ControllerAdviceExceptionHandler {

    /**
     * Обработчик ошибок для {@link RestException}.
     *
     * @param e       ошибка.
     * @param request HTTP-запрос.
     * @return ответ в {@link ErrorDto} в котором нужная информация.
     */
    @ExceptionHandler({RestException.class})
    public ResponseEntity<ErrorDto> handleException(RestException e, HttpServletRequest request) {
        log.error(e.getMessage(), e);

        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var status = new StatusDto();
        status.setMessage(e.getMessage());
        status.setCode(ExceptionCode.UNKNOWN.name());
        if (nonNull(e.getCode())) {
            status.setCode(e.getCode().name());
            httpStatus = e.getCode().getHttpStatus();
        }
        var dto = new ErrorDto();
        dto.setStatus(status);
        dto.setErrors(getErrorFieldDtoRsList(e));
        return ResponseEntity
                .status(httpStatus)
                .body(dto);
    }

    /**
     * Обработчик ошибок для {@link Exception}.
     *
     * @param e       ошибка.
     * @param request HTTP-запрос.
     * @return ответ в {@link ErrorDto} в котором нужная информация.
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorDto> handleOtherExceptions(Exception e, HttpServletRequest request) {
        log.error(toExceptionMessage(e), e);

        var status = new StatusDto();
        status.setMessage(toExceptionMessage(e));
        status.setCode(ExceptionCode.UNKNOWN.name());
        var dto = new ErrorDto();
        dto.setStatus(status);
        dto.setErrors(new ArrayList<>());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(dto);
    }

    private @Nullable List<ErrorFieldDto> getErrorFieldDtoRsList(RestException restException) {
        if (nonNull(restException.getFieldsErrors())) {
            return restException.getFieldsErrors().stream().map(item -> {
                var dto = new ErrorFieldDto();
                dto.setCode(item.getCode());
                dto.setName(item.getName());
                dto.setMessage(item.getMessage());
                return dto;
            }).collect(Collectors.toList());
        }
        return null;
    }

    private String toExceptionMessage(Exception e) {
        return Optional.of(e)
                .map(Exception::getCause)
                .map(Throwable::toString)
                .orElse(e.getMessage());
    }
}
