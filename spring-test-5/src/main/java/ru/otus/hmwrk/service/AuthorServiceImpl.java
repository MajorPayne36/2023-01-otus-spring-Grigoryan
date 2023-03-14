package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.dao.AuthorsDao;
import ru.otus.hmwrk.entity.Author;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorsDao authorsDao;

    @Override
    public List<Author> findAll() {
        return authorsDao.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorsDao.findById(id));
    }

    @Override
    public Optional<Author> save(Author entity) {
        return Optional.ofNullable(authorsDao.save(entity));
    }

    @Override
    public List<Author> findAllAuthorsByBookId(Long bookId) {
        return authorsDao.findAllAuthorsByBookId(bookId);
    }

    @Override
    public void saveWithBookId(Author author, Long bookId) {
        if (findById(author.getId()).isEmpty()) {
            authorsDao.save(author);
        }
        authorsDao.connectToBook(author.getId(), bookId);
    }
}
