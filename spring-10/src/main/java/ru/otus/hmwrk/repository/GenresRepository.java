package ru.otus.hmwrk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.domain.entity.Genre;

import java.util.Optional;

@Repository
public interface GenresRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
