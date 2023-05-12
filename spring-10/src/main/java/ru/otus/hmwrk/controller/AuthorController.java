package ru.otus.hmwrk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hmwrk.domain.mapper.AuthorMapper;
import ru.otus.hmwrk.domain.model.AuthorDto;
import ru.otus.hmwrk.service.AuthorService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorMapper authorMapper;

    private final AuthorService authorService;

    @GetMapping(value = "api/v1/author/list")
    public List<AuthorDto> getBooks() {
        return authorMapper.toDtoList(authorService.findAll());
    }

    @PostMapping(value = "api/v1/author")
    public AuthorDto saveBook(@RequestBody AuthorDto dtoToSave) {
        return Optional.ofNullable(dtoToSave)
                .map(authorMapper::toDao)
                .flatMap(authorService::save)
                .map(authorMapper::toDto)
                .orElse(null);
    }

    @GetMapping(value = "api/v1/author/{id}")
    public AuthorDto getById(@PathVariable("id") Long id) {
        return authorService.getById(id)
                .map(authorMapper::toDto)
                .orElse(null);
    }

    @DeleteMapping(value = "api/v1/author/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        authorService.deleteById(id);
    }
}
