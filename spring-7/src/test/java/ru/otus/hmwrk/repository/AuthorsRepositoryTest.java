package ru.otus.hmwrk.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import ru.otus.hmwrk.entity.Author;

import java.time.LocalDate;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AuthorsRepository.class})
@SqlGroup({
        @Sql(scripts = "classpath:schema.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = "classpath:reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class AuthorsRepositoryTest {

    @Autowired
    private AuthorsRepository authorsDao;

    @Test
    void testFindAll() {
        // given
        var count = 4;

        // when
        var res = StreamSupport.stream(authorsDao.findAll().spliterator(), false).collect(toList());

        // then
        assertThat(res).hasSize(count);
        res.stream().map(Author::getId).forEach(System.out::println);
    }

    @Test
    void testFindById() {
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
                .ignoringFields("books")
                .isEqualTo(currentAuthor);
    }

    @Test
    void testFindByName() {
        // given
        var currentAuthor = new Author()
                .setId(3L)
                .setFirstName("Ivan")
                .setLastName("Fomich")
                .setBirthday(LocalDate.parse("2003-10-29"));

        // when
        var res = authorsDao
                .findByFirstName(currentAuthor.getFirstName())
                .orElse(null);

        // then
        assertThat(res)
                .usingRecursiveComparison()
                .ignoringFields("books", "id")
                .isEqualTo(currentAuthor);
    }

    @Test
    void testSaveWithoutBook() {
        // given
        var currentAuthor = new Author()
                .setLastName("testname")
                .setFirstName("testname")
                .setBirthday(LocalDate.parse("2023-02-11"));

        // when
        var res = authorsDao.save(currentAuthor);
        var findSavedAuthor = authorsDao
                .findByFirstName(currentAuthor.getFirstName())
                .orElse(null);

        // then
        assertThat(findSavedAuthor)
                .usingRecursiveComparison()
                .ignoringFields("id", "books")
                .isEqualTo(currentAuthor);
    }
}