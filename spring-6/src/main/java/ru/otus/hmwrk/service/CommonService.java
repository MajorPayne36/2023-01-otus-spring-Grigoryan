package ru.otus.hmwrk.service;

import java.util.List;
import java.util.Optional;

public interface CommonService <T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> save(T entity);

    void updateNameById(Long id, String name);
}
