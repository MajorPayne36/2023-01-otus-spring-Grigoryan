package ru.otus.hmwrk.service;

import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Optional<Book> save(Book entity);
}
