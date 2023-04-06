package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.AuthorsRepository;
import ru.otus.hmwrk.entity.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService implements CommonService<Author, Long> {

    private final AuthorsRepository authorsRepository;

    @Override
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorsRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<Author> save(Author entity) {
        return Optional.ofNullable(authorsRepository.save(entity));
    }
}