package ru.otus.hmwrk.repository;

import java.util.List;

public interface Repository<T, ID> {
    List<T> findAll();

    T findById(ID id);

    List<T> findByName(String name);

    T save(T entity);
}
