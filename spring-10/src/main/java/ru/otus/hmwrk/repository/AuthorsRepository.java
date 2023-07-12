package ru.otus.hmwrk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.domain.entity.Author;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<List<Author>> findByFirstNameAndLastName (String firstName, String lastName);
}
