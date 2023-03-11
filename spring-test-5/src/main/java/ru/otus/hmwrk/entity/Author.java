package ru.otus.hmwrk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Author {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}
