package ru.otus.hmwrk.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.repository.GenresRepository;
import ru.otus.hmwrk.entity.Genre;
import ru.otus.hmwrk.exceptions.NotValidIdentifierException;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class GenreService implements CommonService<Genre, Long> {

    private final GenresRepository genresRepository;

    @Override
    public List<Genre> findAll() {
        return genresRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(genresRepository.findById(id));
    }

    @Override
    @Transactional
    public Optional<Genre> save(Genre entity) {
        return Optional.ofNullable(genresRepository.save(entity));
    }

    @Override
    @Transactional
    public void updateNameById(Long id, String name) {
        if (nonNull(id)) {
            var genre = findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));
            genre.setName(name);
            save(genre);
        } else {
            throw new NotValidIdentifierException(id);
        }
    }
}