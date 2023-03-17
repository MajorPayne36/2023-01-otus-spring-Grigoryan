package ru.otus.hmwrk.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorsDao implements Dao<Author, Long> {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Author> findAll() {
        String sql = "SELECT id, birthday, first_name, last_name FROM authors ";
        var authors = jdbc.query(sql, new AuthorMapper());
        return authors;
    }

    @Override
    public Author findById(Long id) {
            String sql = "SELECT id, birthday, first_name, last_name FROM authors WHERE id = :id ";
            return jdbc.queryForObject(sql, Map.of("id", id), new AuthorMapper());
    }

    @Override
    public Author save(Author entity) {
            var params = new java.util.HashMap<String, Object>(Map.of(
                    "id", entity.getId(),
                    "birthday", entity.getBirthday(),
                    "firstName", entity.getFirstName(),
                    "lastName", entity.getLastName()
            ));
            String sql = "INSERT INTO authors (id, birthday, first_name, last_name) " +
                    "VALUES (:id, :birthday, :firstName, :lastName) ";
            jdbc.update(sql, params);

            return findById(entity.getId());
    }

    public List<Author> findAllAuthorsByBookId(Long bookId){
        String sql = "SELECT id, birthday, first_name, last_name FROM authors a " +
                "LEFT JOIN books_id_authors_id biai ON a.id = biai.author_id " +
                "WHERE biai.book_id = :bookId ";
        return jdbc.query(sql, Map.of("bookId", bookId), new AuthorMapper());
    }

    public void connectToBook(Long authorId, Long bookId) {
        String sql = "INSERT INTO books_id_authors_id (book_id, author_id) " +
                "VALUES (:bookId, :authorId) ";
        var params = Map.of(
                "bookId", bookId,
                "authorId", authorId
        );
        jdbc.update(sql, params);
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Author()
                    .setId(rs.getLong("id"))
                    .setBirthday(rs.getDate("birthday").toLocalDate())
                    .setFirstName(rs.getString("first_name"))
                    .setLastName(rs.getString("last_name"));
        }
    }
}
