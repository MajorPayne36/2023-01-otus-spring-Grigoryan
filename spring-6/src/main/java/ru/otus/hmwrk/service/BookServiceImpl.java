package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.BooksDao;
import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksDao booksDao;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
       return booksDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(booksDao.findById(id));
    }

    @Override
    @Transactional
    public Optional<Book> save(Book entity) {
        return Optional.ofNullable(booksDao.save(entity));
    }
}
