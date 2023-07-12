package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.domain.entity.Book;
import ru.otus.hmwrk.repository.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService implements CrudService<Book, Long> {

    private final BooksRepository booksRepository;

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public Optional<Book> save(Book entity) {
        return Optional.of(booksRepository.save(entity));
    }

    @Override
    public Optional<Book> getById(Long id) {
        return booksRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        booksRepository.deleteById(id);
    }
}
