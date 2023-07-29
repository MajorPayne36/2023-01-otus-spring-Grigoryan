package ru.otus.eba.mapper;

import org.mapstruct.Mapper;
import ru.otus.eba.dto.openapi.PersonDto;
import ru.otus.eba.dto.openapi.VerificationResultDto;

@Mapper(config = BaseMapperConfig.class)
public interface VerificationMapper {

    VerificationResultDto toResult(PersonDto personDto);
}
