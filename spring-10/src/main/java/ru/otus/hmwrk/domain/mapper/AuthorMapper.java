package ru.otus.hmwrk.domain.mapper;

import org.mapstruct.Mapper;
import ru.otus.hmwrk.domain.entity.Author;
import ru.otus.hmwrk.domain.model.AuthorDto;

import java.util.List;

@Mapper(uses = {
        BookMapper.class
})
public interface AuthorMapper {
    Author toDao (AuthorDto dto);

    AuthorDto toDto (Author entity);

    List<AuthorDto> toDtoList (List<Author> entityList);

    List<Author> toDaoList(List<AuthorDto> dtoList);
}
