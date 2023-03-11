package ru.otus.hmwrk.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Класс сохраняющий сущности типа {@link Book}.
 */
@Repository
@RequiredArgsConstructor
public class BooksDao implements Dao<Book, Long> {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Book> findAll() {
        String sql = "SELECT id, name, publication_date FROM books";
        var books = jdbc.query(sql, new BookRowMapper());
        return books;
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
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"))
                    .setPublicationDate(rs.getDate("publication_date").toLocalDate());
        }
    }
}
