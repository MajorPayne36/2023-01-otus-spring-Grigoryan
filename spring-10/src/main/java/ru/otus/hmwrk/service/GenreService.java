package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.domain.entity.Genre;
import ru.otus.hmwrk.repository.GenresRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService implements CrudService<Genre, Long> {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genresRepository.findById(id);
    }

    @Override
    public Optional<Genre> save(Genre entity) {
        return Optional.of(genresRepository.save(entity));
    }

    @Override
    public Optional<Genre> getById(Long id) {
        return genresRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        genresRepository.deleteById(id);
    }
}