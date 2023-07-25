package ru.otus.eba.domain.mapper;

import javax.annotation.processing.Generated;
import ru.otus.eba.domain.dto.PersonDto;
import ru.otus.eba.domain.entity.Person;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-13T16:50:21+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 11 (Oracle Corporation)"
)
public class PersonMapperImpl implements PersonMapper {

    @Override
    public Person toEntity(PersonDto dto) {
        if ( dto == null ) {
            return null;
        }

        Person person = new Person();

        return person;
    }

    @Override
    public PersonDto toDto(Person entity) {
        if ( entity == null ) {
            return null;
        }

        PersonDto personDto = new PersonDto();

        return personDto;
    }
}
