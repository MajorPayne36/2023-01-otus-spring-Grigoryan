package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.GenresRepository;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService implements CommonService<Genre, Long> {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(genresRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<Genre> save(Genre entity) {
        return Optional.ofNullable(genresRepository.save(entity));
    }
}