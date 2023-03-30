package ru.otus.hmwrk.service;

import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();

    Optional<Author> findById(Long id);

    Optional<Author> save(Author entity);
}
