package ru.otus.hmwrk.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.BooksRepository;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.exceptions.NotValidIdentifierException;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class BookService implements CommonService<Book, Long> {

    private final BooksRepository booksRepository;

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(booksRepository.findById(id));
    }

    @Override
    public Optional<Book> save(Book entity) {
        return Optional.ofNullable(booksRepository.save(entity));
    }

    @Override
    public void updateNameById(Long id, String name) {
        if (nonNull(id)) {
            var book = findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));
            book.setName(name);
            save(book);
        } else {
            throw new NotValidIdentifierException(id);
        }
    }
}
