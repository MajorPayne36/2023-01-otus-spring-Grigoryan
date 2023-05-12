package ru.otus.hmwrk.domain.model;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;

    private String content;

    private BookDto book;
}
