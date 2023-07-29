package ru.otus.eba.service;

import ru.otus.eba.dto.openapi.VerificationDto;
import ru.otus.eba.dto.openapi.VerificationResultDto;

/**
 * Сервис верификации пользователя.
 */
public interface VerificationService {
    /**
     * Верификация пользователя.
     *
     * @param verifyData данные для верификации.
     * @return результат верификации.
     */
    VerificationResultDto verifyPerson(VerificationDto verifyData);
}
