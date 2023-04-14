package ru.otus.hmwrk.shell;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.entity.Comment;
import ru.otus.hmwrk.service.BookService;

import java.time.LocalDate;

import static java.util.stream.Collectors.joining;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
    private static final String COMMENT_TMPLT = "Comment: %s \n";
    private final BookService bookService;

    @ShellMethod(value = "Get All Books", key = {"fa", "findAll"})
    public String findAll() {
        return bookService.findAll().toString();
    }

    @ShellMethod(value = "Get Book by id", key = {"fbi", "findById"})
    public String findById(@ShellOption Long id) {
        return bookService.findById(id).toString();
    }

    @ShellMethod(value = "Save Book", key = {"save", "s"})
    public String save(@ShellOption Long id, @ShellOption String name, @ShellOption String pubDate) {
        return bookService.save(new Book()
                        .setId(id)
                        .setName(name)
                        .setPublicationDate(LocalDate.parse(pubDate)))
                .toString();
    }


    @Transactional(readOnly = true)
    @ShellMethod(value = "Get Book by id", key = {"fcbi", "findCommentsByBookId"})
    public String findCommentsByBookId(@ShellOption Long id) {
        return bookService.findById(id)
                .map(Book::getComments)
                .map(e -> e.stream().map(Comment::getContent))
                .map(e -> e.map(s -> String.format(COMMENT_TMPLT, s)))
                .map(e -> e.collect(joining()))
                .orElse(Strings.EMPTY);
    }
}