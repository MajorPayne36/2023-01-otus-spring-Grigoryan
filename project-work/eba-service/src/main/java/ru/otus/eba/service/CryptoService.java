package ru.otus.eba.service;

import ru.otus.eba.dto.openapi.CipherToolDto;
import ru.otus.eba.dto.openapi.CipherToolDto.OperationTypeEnum;
import ru.otus.eba.dto.openapi.CryptoKeysDto;

/**
 * Сервис для генерации ключей.
 */
public interface CryptoService {
    /**
     * Генерация ключей.
     *
     * @return Объект содержащий открытый и закрытый ключи.
     */
    CryptoKeysDto generateKeys();

    /**
     * Метод для обработки шифрования/расшифрования переданного текста переданным ключом.
     * Действие зависит от {@link OperationTypeEnum}
     *
     * @param cipherToolDto данные для обработки.
     * @return результат выполнения операции.
     */
    String processOperation(CipherToolDto cipherToolDto);
}
