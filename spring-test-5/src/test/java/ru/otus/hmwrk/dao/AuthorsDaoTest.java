package ru.otus.hmwrk.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.hmwrk.entity.Author;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import(AuthorsDao.class)
class AuthorsDaoTest {

    @Autowired
    private AuthorsDao authorsDao;

    @Test
    void findAll() {
        // given
        var count = 4;

        // when
        var res = authorsDao.findAll();

        // then
        assertThat(res).hasSize(count);
    }

    @Test
    void findById() {
        // given
        var currentAuthor = new Author()
                .setId(3L)
                .setFirstName("Ivan")
                .setLastName("Fomich")
                .setBirthday(LocalDate.parse("2003-10-29"));

        // when
        var res = authorsDao.findById(currentAuthor.getId());

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(currentAuthor);
    }

    @Test
    void save() {
        // given
        var currentAuthor = new Author()
                .setId(5L)
                .setLastName("last name")
                .setFirstName("first name")
                .setBirthday(LocalDate.parse("2023-02-11"));

        // when
        var res = authorsDao.save(currentAuthor);

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(currentAuthor);
    }

    @Test
    void findAllAuthorsByBookId() {
        // given
        var bookId = 1L;
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

        // when
        List<Author> res = authorsDao.findAllAuthorsByBookId(bookId);

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .isEqualTo(listOfAuthors);
    }
}