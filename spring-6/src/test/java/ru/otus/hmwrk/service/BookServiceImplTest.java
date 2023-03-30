package ru.otus.hmwrk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.hmwrk.dao.BooksDao;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.entity.Genre;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private final Book book = new Book()
            .setId(1L)
            .setName("book 1")
            .setPublicationDate(LocalDate.parse("2023-02-11"));

    @Mock
    private BooksDao booksDao;
    @Mock
    private AuthorService authorService;
    @Mock
    private GenreService genreService;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void mockAll() {
        lenient().when(booksDao.findAll()).thenReturn(List.of(book));
        lenient().when(booksDao.findById(anyLong())).thenReturn(book);
        lenient().when(booksDao.save(any())).thenReturn(book);
    }

    @Test
    void findAll() {
        // when
        var res = bookService.findAll();

        // then
        assertThat(res.get(0).getId()).isEqualTo(book.getId());
    }

    @Test
    void findById() {
        // when
        var res = bookService.findById(1L);

        // then
        assertThat(res.orElseGet(null).getId()).isEqualTo(book.getId());
    }

    @Test
    void save() {
        // when
        var res = bookService.save(book);

        // then
        assertThat(res.orElseGet(null).getId()).isEqualTo(book.getId());
    }
}