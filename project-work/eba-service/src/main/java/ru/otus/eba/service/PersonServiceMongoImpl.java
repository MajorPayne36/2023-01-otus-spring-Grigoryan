package ru.otus.eba.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.eba.dto.openapi.PersonDto;
import ru.otus.eba.exception.PersonException;
import ru.otus.eba.mapper.PersonMapper;
import ru.otus.eba.repository.PersonMongoRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class PersonServiceMongoImpl implements PersonService {
    private final PersonMongoRepository repository;
    private final PersonMapper mapper;

    @Override
    public PersonDto getByEmail(String email){
        return Optional.ofNullable(email)
                .flatMap(repository::findFirstByEmail)
                .map(mapper::toDto)
                .orElseThrow(()-> new PersonException("Not found person with email: " + email));
    }

    @Override
    public List<PersonDto> getAll(){
        return Optional.of(repository.findAll())
                .map(mapper::toDtoList)
                .orElse(emptyList());
    }

    @Override
    public PersonDto save(PersonDto dto){
        return Optional.of(dto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(mapper::toDto)
                .orElseThrow(() -> new PersonException("Error while saving person"));
    }
}
