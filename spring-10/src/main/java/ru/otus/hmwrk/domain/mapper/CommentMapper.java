package ru.otus.hmwrk.domain.mapper;

import org.mapstruct.Mapper;
import ru.otus.hmwrk.domain.entity.Comment;
import ru.otus.hmwrk.domain.model.CommentDto;

import java.util.List;

@Mapper(uses = {
        BookMapper.class
})
public interface CommentMapper {
    Comment toDao(CommentDto dto);

    CommentDto toDto(Comment entity);

    List<CommentDto> toDtoList(List<Comment> entityList);

    List<Comment> toDaoList(List<CommentDto> dtoList);
}
