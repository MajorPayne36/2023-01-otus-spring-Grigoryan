package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.AuthorsDao;
import ru.otus.hmwrk.entity.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorsDao authorsDao;

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorsDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorsDao.findById(id));
    }

    @Override
    @Transactional
    public Optional<Author> save(Author entity) {
        return Optional.ofNullable(authorsDao.save(entity));
    }
}