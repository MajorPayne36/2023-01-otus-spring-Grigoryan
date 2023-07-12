package ru.otus.hmwrk.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    private Long id;

    private String name;

    private LocalDate publicationDate;

    private List<AuthorDto> authors = new ArrayList<>();

    private List<GenreDto> genres = new ArrayList<>();

    private List<CommentDto> comments = new ArrayList<>();
}
