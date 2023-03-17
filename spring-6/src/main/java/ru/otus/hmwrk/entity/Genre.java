package ru.otus.hmwrk.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Genre {
    private Long id;
    private String name;
    private Book book;
}
