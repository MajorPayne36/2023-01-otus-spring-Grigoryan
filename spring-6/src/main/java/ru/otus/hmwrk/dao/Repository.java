package ru.otus.hmwrk.dao;

import java.util.List;

public interface Repository<T, ID> {
    List<T> findAll();
    T findById(ID id);

    List<T> findByName(String name);

    void updateNameById(ID id, String name);

    T save(T entity);
}
