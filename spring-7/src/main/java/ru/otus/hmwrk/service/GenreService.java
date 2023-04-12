package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.GenresRepository;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class GenreService implements CrudService<Genre, Long> {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> findAll() {
        return StreamSupport.stream(genresRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genresRepository.findById(id);
    }

    @Override
    public Optional<Genre> save(Genre entity) {
        return Optional.of(genresRepository.save(entity));
    }
}