package ru.otus.hmwrk.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDto {
    private Long id;

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private List<BookDto> books = new ArrayList<>();
}
