package ru.otus.hmwrk.domain.mapper;

import org.mapstruct.Mapper;
import ru.otus.hmwrk.domain.entity.Genre;
import ru.otus.hmwrk.domain.model.GenreDto;

import java.util.List;

@Mapper(uses = {
        BookMapper.class
})
public interface GenreMapper {
    Genre toDao(GenreDto dto);

    GenreDto toDto(Genre entity);

    List<GenreDto> toDtoList(List<Genre> entityList);

    List<Genre> toDaoList(List<GenreDto> dtoList);
}
