package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.BooksRepository;
import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class BookService implements CrudService<Book, Long> {

    private final BooksRepository booksRepository;

    @Override
    public List<Book> findAll() {
        return StreamSupport.stream(booksRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public Optional<Book> save(Book entity) {
        return Optional.of(booksRepository.save(entity));
    }
}
