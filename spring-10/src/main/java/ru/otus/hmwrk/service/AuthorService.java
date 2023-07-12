package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.domain.entity.Author;
import ru.otus.hmwrk.repository.AuthorsRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService implements CrudService<Author, Long> {

    private final AuthorsRepository authorsRepository;

    @Override
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorsRepository.findById(id);
    }

    @Override
    public Optional<Author> save(Author entity) {
        return Optional.of(authorsRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        authorsRepository.deleteById(id);
    }

    @Override
    public Optional<Author> getById(Long id) {
        return authorsRepository.findById(id);
    }
}