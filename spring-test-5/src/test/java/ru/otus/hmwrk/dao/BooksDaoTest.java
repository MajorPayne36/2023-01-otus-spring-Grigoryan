package ru.otus.hmwrk.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hmwrk.entity.Book;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(BooksDao.class)
class BooksDaoTest {

    @Autowired
    private BooksDao booksDao;

    @Test
    void testFindAll() {
        // given
        var count = 5;

        // when
        var res = booksDao.findAll();

        // then
        assertThat(res).hasSize(count);
    }

    @Test
    void testFindById() {
        // given
        var currentBook = new Book()
                .setId(1L)
                .setName("book 1")
                .setPublicationDate(LocalDate.parse("2023-02-11"));

        // when
        var res = booksDao.findById(currentBook.getId());

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(currentBook);
    }

    @Test
    void testSave() {
        // given
        var currentBook = new Book()
                .setId(6L)
                .setName("book 6")
                .setPublicationDate(LocalDate.parse("2023-02-16"));

        // when
        var res = booksDao.save(currentBook);

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(currentBook);
    }
}