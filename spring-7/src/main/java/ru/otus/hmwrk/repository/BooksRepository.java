package ru.otus.hmwrk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.entity.Genre;

import java.util.Optional;


/**
 * Класс сохраняющий сущности типа {@link Book}.
 */
@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);
    Long countByGenresId(Long id);
}
