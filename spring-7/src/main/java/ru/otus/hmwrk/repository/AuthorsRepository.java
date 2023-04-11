package ru.otus.hmwrk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Author;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByFirstName (String firstName);
}
