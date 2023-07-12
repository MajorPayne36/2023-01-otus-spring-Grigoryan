package ru.otus.hmwrk.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.hmwrk.domain.mapper.BookMapper;
import ru.otus.hmwrk.domain.model.BookDto;
import ru.otus.hmwrk.service.BookService;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookMapper bookMapper;

    private final BookService bookService;

    @GetMapping(value = "api/v1/book/list")
    public List<BookDto> getBooks() {
        return bookMapper.toDtoList(bookService.findAll());
    }

    @PostMapping(value = "api/v1/book")
    public BookDto saveBook(@RequestBody BookDto dtoToSave) {
        return Optional.ofNullable(dtoToSave)
                .map(bookMapper::toDao)
                .flatMap(bookService::save)
                .map(bookMapper::toDto)
                .orElse(null);
    }

    @GetMapping(value = "api/v1/book/{id}")
    public BookDto getById(@PathVariable("id") Long id) {
        return bookService.getById(id)
                .map(bookMapper::toDto)
                .orElse(null);
    }

    @DeleteMapping(value = "api/v1/book/{id}")
    public void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
    }
}
