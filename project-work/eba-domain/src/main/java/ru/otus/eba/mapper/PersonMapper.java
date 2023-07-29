package ru.otus.eba.mapper;

import org.mapstruct.Mapper;
import ru.otus.eba.domain.entity.Person;
import ru.otus.eba.dto.openapi.PersonDto;

import java.util.List;

@Mapper(config = BaseMapperConfig.class)
public interface PersonMapper {

    Person toEntity(PersonDto dto);

    PersonDto toDto(Person entity);

    List<PersonDto> toDtoList(List<Person> entity);
}
