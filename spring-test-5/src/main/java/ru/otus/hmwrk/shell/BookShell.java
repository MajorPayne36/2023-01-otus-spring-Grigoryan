package ru.otus.hmwrk.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.hmwrk.entity.Book;
import ru.otus.hmwrk.service.BookService;

import java.time.LocalDate;

@ShellComponent
@RequiredArgsConstructor
public class BookShell {
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
}