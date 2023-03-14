package ru.otus.hmwrk.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T save(T entity);
}
