package ru.otus.hmwrk.service;

import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Optional<Genre> save(Genre entity);

    List<Genre> findAllGenresByBookId(Long bookId);

    void saveWithBookId(Genre genre, Long bookId);
}
