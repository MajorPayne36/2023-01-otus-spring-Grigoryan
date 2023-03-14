package ru.otus.hmwrk.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.h2.jdbc.JdbcSQLSyntaxErrorException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * Класс сохраняющий сущности типа {@link Book}.
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class BooksDao implements Dao<Book, Long> {

    public static final String AUTHOR_FIRST_NAME = "author_first_name";
    private static final String AUTHOR_LAST_NAME = "author_last_name";
    private static final String GENRE_ID = "genre_id";
    private static final String GENRE_NAME = "genre_name";
    private static final String AUTHOR_BRTHD = "author_brthd";
    private static final String AUTHOR_ID = "author_id";

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> findAll() {
        var bookRowMapper = new BookRowMapper();
        String sql = "SELECT " +
                "       b.id, " +
                "       b.name, " +
                "       b.publication_date, " +
                "       a.id as " + AUTHOR_ID + ", " +
                "       a.birthday as " + AUTHOR_BRTHD + ", " +
                "       a.first_name as " + AUTHOR_FIRST_NAME + ", " +
                "       a.last_name as " + AUTHOR_LAST_NAME + ", " +
                "       g.id as " + GENRE_ID + ", " +
                "       g.name as " + GENRE_NAME + " " +
                "FROM books b " +
                "   LEFT JOIN books_id_genres_id bigi ON b.id = bigi.book_id " +
                "   INNER JOIN genres g ON g.id = bigi.genre_id " +
                "   LEFT JOIN books_id_authors_id biai ON b.id = biai.book_id " +
                "   INNER JOIN authors a ON a.id = biai.author_id ";
        jdbc.query(sql, bookRowMapper);

        return new ArrayList<>(bookRowMapper.booksSet);
    }

    @Override
    public Book findById(Long id) {
        String sql = "SELECT id, name, publication_date FROM books WHERE id = :id";
        return jdbc.queryForObject(sql, Map.of("id", id), new BookRowMapper());
    }

    @Override
    public Book save(Book entity) {
        var params = Map.of(
                "id", entity.getId(),
                "name", entity.getName(),
                "publicationDate", entity.getPublicationDate()
        );
        String sql = "INSERT INTO books (id, name, publication_date) VALUES (:id, :name, :publicationDate)";
        jdbc.update(sql, params);
        return findById(entity.getId());
    }

    private static class BookRowMapper implements RowMapper<Book> {
        private final Set<Book> booksSet = new HashSet<>();

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            var book = new Book()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setPublicationDate(rs.getDate("publication_date").toLocalDate());
            if (booksSet.stream().noneMatch(b -> Objects.equals(b.getId(), book.getId()))) {
                booksSet.add(book);
            }

            collectAuthor(rs, book);
            collectGenre(rs, book);
            return book;
        }

        private void collectGenre(ResultSet rs, Book book) throws SQLException {
            try {

                var genre = new Genre()
                        .setId(rs.getLong(GENRE_ID))
                        .setName(rs.getString(GENRE_NAME))
                        .setBook(book);
                if (booksSet.contains(book)) {
                    booksSet.stream()
                            .filter(e -> Objects.equals(e.getId(), book.getId()))
                            .forEach(e -> e.addGenre(genre));
                }
            } catch (JdbcSQLSyntaxErrorException ex) {
                log.error("Error while parsing genres from BookRowMapper. An exception is: {}", ex.getMessage());
            }
        }

        private void collectAuthor(ResultSet rs, Book book) throws SQLException {
            try {
                var author = new Author()
                        .setId(rs.getLong(AUTHOR_ID))
                        .setBirthday(rs.getDate(AUTHOR_BRTHD).toLocalDate())
                        .setFirstName(rs.getString(AUTHOR_FIRST_NAME))
                        .setLastName(rs.getString(AUTHOR_LAST_NAME))
                        .setBook(book);
                if (booksSet.contains(book)) {
                    booksSet.stream()
                            .filter(e -> Objects.equals(e.getId(), book.getId()))
                            .forEach(e -> e.addAuthor(author));
                }
            } catch (JdbcSQLSyntaxErrorException ex) {
                log.error("Error while parsing authors from BookRowMapper. An exception is: {}", ex.getMessage());
            }
        }
    }
}
