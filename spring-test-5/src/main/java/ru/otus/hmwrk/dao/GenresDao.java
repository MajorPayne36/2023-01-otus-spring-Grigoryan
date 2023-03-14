package ru.otus.hmwrk.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.entity.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenresDao implements Dao<Genre, Long> {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT id, name FROM genres";
        var genres = jdbc.query(sql, new GenreRowMapper());
        return genres;
    }

    @Override
    public Genre findById(Long id) {
        String sql = "SELECT id, name FROM genres WHERE id = :id";
        return jdbc.queryForObject(sql, Map.of("id", id), new GenreRowMapper());
    }

    @Override
    public Genre save(Genre entity) {
        var params = Map.of(
                "id", entity.getId(),
                "name", entity.getName()
        );
        String sql = "INSERT INTO books (id, name) VALUES (:id, :name)";
        jdbc.update(sql, params);
        return findById(entity.getId());
    }

    public List<Genre> findAllGenresByBookId(Long bookId){
        String sql = "SELECT id, name FROM genres g " +
                "LEFT JOIN books_id_genres_id bigi ON g.id = bigi.genre_id " +
                "WHERE bigi.book_id = :bookId ";
        return jdbc.query(sql, Map.of("bookId", bookId), new GenreRowMapper());
    }

    public void connectToBook(Long genreId, Long bookId) {
        String sql = "INSERT INTO books_id_genres_id (book_id, genre_id) " +
                "VALUES (:bookId, :genreId) ";
        var params = Map.of(
                "bookId", bookId,
                "genreId", genreId
        );
        jdbc.update(sql, params);
    }

    private static class GenreRowMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Genre()
                    .setId(rs.getLong("id"))
                    .setName(rs.getString("name"));
        }
    }
}
