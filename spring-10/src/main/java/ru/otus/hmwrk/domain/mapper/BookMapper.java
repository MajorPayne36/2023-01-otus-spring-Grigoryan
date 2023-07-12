package ru.otus.hmwrk.domain.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.otus.hmwrk.domain.entity.Author;
import ru.otus.hmwrk.domain.entity.Book;
import ru.otus.hmwrk.domain.entity.Comment;
import ru.otus.hmwrk.domain.entity.Genre;
import ru.otus.hmwrk.domain.model.AuthorDto;
import ru.otus.hmwrk.domain.model.BookDto;
import ru.otus.hmwrk.domain.model.CommentDto;
import ru.otus.hmwrk.domain.model.GenreDto;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BookMapper {
    Book toDao(BookDto dto);

    @Mapping(target = "authors", qualifiedByName = "mapAuthorsWithoutBooks")
    @Mapping(target = "genres", qualifiedByName = "mapGenresWithoutBooks")
    @Mapping(target = "comments", qualifiedByName = "mapCommentsWithoutBooks")
    BookDto toDto(Book entity);

    List<BookDto> toDtoList(List<Book> entityList);

    List<Book> toDaoList(List<BookDto> dtoList);

    @Named("mapAuthorsWithoutBooks")
    @Mapping(target = "books", ignore = true)
    AuthorDto mapAuthorsWithoutBooks(Author entity);

    @Named("mapGenresWithoutBooks")
    @Mapping(target = "books", ignore = true)
    GenreDto mapGenresWithoutBooks(Genre entity);

    @Named("mapCommentsWithoutBooks")
    @Mapping(target = "book", ignore = true)
    CommentDto mapCommentsWithoutBooks(Comment entity);
}
