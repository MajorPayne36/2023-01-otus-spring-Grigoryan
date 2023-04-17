package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.AuthorsRepository;
import ru.otus.hmwrk.entity.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class AuthorService implements CrudService<Author, Long> {

    private final AuthorsRepository authorsRepository;

    @Override
    public List<Author> findAll() {
        return StreamSupport.stream(authorsRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorsRepository.findById(id);
    }

    @Override
    public Optional<Author> save(Author entity) {
        return Optional.of(authorsRepository.save(entity));
    }
}