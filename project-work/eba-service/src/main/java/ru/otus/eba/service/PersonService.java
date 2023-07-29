package ru.otus.eba.service;

import ru.otus.eba.dto.openapi.PersonDto;

import java.util.List;

/**
 * Сервис для работы с пользователями.
 */
public interface PersonService {
    /**
     * Поиск пользователя по ел. почте.
     *
     * @param email эл. почта пользователя.
     * @return найденный пользователь или null.
     */
    PersonDto getByEmail(String email);

    /**
     * Возвращает всех пользователей.
     *
     * @return список пользователей.
     */
    List<PersonDto> getAll();

    /**
     * Сохраняет пользователя.
     *
     * @param dto данный для сохранения.
     * @return сохраненный обьект.
     */
    PersonDto save(PersonDto dto);
}
