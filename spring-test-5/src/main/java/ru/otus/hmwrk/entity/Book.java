package ru.otus.hmwrk.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Book {
    private Long id;
    private String name;
    private LocalDate publicationDate;
    private List<Author> authors = new ArrayList<>();
    private List<Genre> genres = new ArrayList<>();

    public Boolean addAuthor(Author author) {
        if (Objects.nonNull(author) && !authors.contains(author)) {
            authors.add(author);
            return true;
        }
        return false;
    }

    public Boolean addGenre(Genre genre) {
        if (Objects.nonNull(genre) && !genres.contains(genre)) {
            genres.add(genre);
            return true;
        }
        return false;
    }
}
