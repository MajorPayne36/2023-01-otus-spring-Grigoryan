package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.dao.BooksDao;
import ru.otus.hmwrk.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksDao booksDao;
    private final AuthorService authorService;
    private final GenreService genreService;

    @Override
    public List<Book> findAll() {
       return booksDao.findAll().stream().map(this::extendBookWithAuthorsAndGenres).collect(toList());
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional
                .ofNullable(booksDao.findById(id))
                .map(this::extendBookWithAuthorsAndGenres);
    }

    @Override
    public Optional<Book> save(Book entity) {
        entity.getAuthors().forEach(e -> authorService.saveWithBookId(e, entity.getId()));
        entity.getGenres().forEach(e -> genreService.saveWithBookId(e, entity.getId()));
        return Optional
                .ofNullable(booksDao.save(entity))
                .map(this::extendBookWithAuthorsAndGenres);
    }

    public Book extendBookWithAuthorsAndGenres(Book book){
        var authors = authorService.findAllAuthorsByBookId(book.getId());
        var genres = genreService.findAllGenresByBookId(book.getId());

        authors.forEach(book::addAuthor);
        genres.forEach(book::addGenre);

        return book;
    }
}
