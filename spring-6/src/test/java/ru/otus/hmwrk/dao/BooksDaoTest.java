package ru.otus.hmwrk.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Book;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BooksDao.class, AuthorsDao.class})
class BooksDaoTest {
    private static final String BOOK_NAME = "book 1";

    @Autowired
    private BooksDao booksDao;

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void testFindAll() {
        // given
        var count = 5;

        // when
        var res = booksDao.findAll();

        // then
        assertThat(res).hasSize(count);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void testFindById() {
        // given
        var listOfAuthors = List.of(
                new Author()
                        .setId(1L)
                        .setFirstName("Andranik")
                        .setLastName("Grigoryan")
                        .setBirthday(LocalDate.parse("2000-10-29")),
                new Author()
                        .setId(2L)
                        .setFirstName("Vasya")
                        .setLastName("Vasin")
                        .setBirthday(LocalDate.parse("2002-10-29"))
        );
        var currentBook = new Book()
                .setId(1L)
                .setName("book 1")
                .setPublicationDate(LocalDate.parse("2023-02-11"))
                .setAuthors(listOfAuthors);

        // when
        var res = booksDao.findById(currentBook.getId());

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .ignoringFields("authors", "genres")
                .isEqualTo(currentBook);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
    void testFindByName() {
        // given
        var currentBook = new Book()
                .setId(1L)
                .setName("book 1")
                .setPublicationDate(LocalDate.parse("2023-02-11"));

        // when
        var res = booksDao.findByName(BOOK_NAME).get(0);

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .ignoringFields("authors", "genres")
                .isEqualTo(currentBook);
    }

    @Test
    @SqlGroup({
            @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            @Sql(scripts = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    })
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