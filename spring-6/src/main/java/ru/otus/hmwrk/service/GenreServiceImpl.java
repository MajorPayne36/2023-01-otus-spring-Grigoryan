package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.GenresDao;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenresDao genresDao;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genresDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(genresDao.findById(id));
    }

    @Override
    @Transactional
    public Optional<Genre> save(Genre entity) {
        return Optional.ofNullable(genresDao.save(entity));
    }
}