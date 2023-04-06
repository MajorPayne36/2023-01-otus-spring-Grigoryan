package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.BooksRepository;
import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements CommonService<Book, Long> {

    private final BooksRepository booksRepository;

    @Override
    public List<Book> findAll() {
       return booksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(booksRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<Book> save(Book entity) {
        return Optional.ofNullable(booksRepository.save(entity));
    }
}
