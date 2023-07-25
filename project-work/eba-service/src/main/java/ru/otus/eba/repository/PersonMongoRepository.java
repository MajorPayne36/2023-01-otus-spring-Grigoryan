package ru.otus.eba.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.eba.domain.entity.Person;

import java.util.Optional;

public interface PersonMongoRepository extends MongoRepository<Person, String> {
    Optional<Person> findFirstByEmail(String email);
}
