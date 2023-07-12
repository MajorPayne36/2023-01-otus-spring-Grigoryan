package ru.otus.hmwrk.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID> {
    List<T> findAll();

    Optional<T> findById(ID id);

    Optional<T> save(T entity);

    void deleteById(ID id);

    Optional<T> getById(ID id);
}
