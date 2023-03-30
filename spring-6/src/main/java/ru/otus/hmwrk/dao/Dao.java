package ru.otus.hmwrk.dao;

import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    List<T> findAll();
    T findById(ID id);

    List<T> findByName(String name);

    void updateNameById(ID id, String name);

    T save(T entity);
}
