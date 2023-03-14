package ru.otus.hmwrk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private Book book;
}
