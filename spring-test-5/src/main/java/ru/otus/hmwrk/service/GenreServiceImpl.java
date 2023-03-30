package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.dao.GenresDao;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenresDao genresDao;

    @Override
    public List<Genre> findAll() {
        return genresDao.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(genresDao.findById(id));
    }

    @Override
    public Optional<Genre> save(Genre entity) {
        return Optional.ofNullable(genresDao.save(entity));
    }

    @Override
    public List<Genre> findAllGenresByBookId(Long bookId) {
        return genresDao.findAllGenresByBookId(bookId);
    }

    @Override
    public void saveWithBookId(Genre genre, Long bookId) {
        if (findById(genre.getId()).isEmpty()) {
            genresDao.save(genre);
        }
        genresDao.connectToBook(genre.getId(), bookId);
    }
}
