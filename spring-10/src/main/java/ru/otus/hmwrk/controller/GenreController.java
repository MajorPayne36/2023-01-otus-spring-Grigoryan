package ru.otus.hmwrk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hmwrk.domain.mapper.GenreMapper;
import ru.otus.hmwrk.domain.model.GenreDto;
import ru.otus.hmwrk.service.GenreService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class GenreController {
    private final GenreMapper genreMapper;

    private final GenreService genreService;

    @GetMapping(value = "api/v1/genre/list")
    public List<GenreDto> getGenres() {
        return genreMapper.toDtoList(genreService.findAll());
    }

    @PostMapping(value = "api/v1/genre")
    public GenreDto saveGenre(@RequestBody GenreDto dtoToSave) {
        return Optional.ofNullable(dtoToSave)
                .map(genreMapper::toDao)
                .flatMap(genreService::save)
                .map(genreMapper::toDto)
                .orElse(null);
    }

    @GetMapping(value = "api/v1/genre/{id}")
    public GenreDto getById(@PathVariable("id") Long id) {
        return genreService.getById(id)
                .map(genreMapper::toDto)
                .orElse(null);
    }

    @DeleteMapping(value = "api/v1/genre/{id}")
    public void deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteById(id);
    }
}


