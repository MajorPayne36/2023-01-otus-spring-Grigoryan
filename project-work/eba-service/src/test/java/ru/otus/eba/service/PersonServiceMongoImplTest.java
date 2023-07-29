package ru.otus.eba.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.eba.domain.entity.Person;
import ru.otus.eba.dto.openapi.PersonDto;
import ru.otus.eba.exception.PersonException;
import ru.otus.eba.mapper.PersonMapper;
import ru.otus.eba.repository.PersonMongoRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static ru.otus.eba.util.TestKeysUtils.TEST_PUBLIC_KEY;

@ExtendWith(MockitoExtension.class)
class PersonServiceMongoImplTest {

    private final EasyRandom easyRandom = new EasyRandom();

    @Mock
    private PersonMongoRepository repository;
    @Mock
    private PersonMapper mapper;
    @InjectMocks
    private PersonServiceMongoImpl service;

    @Test
    void getByEmail() {
        // given
        var email = "test";
        var person = easyRandom.nextObject(Person.class);
        person.setPublicKey(TEST_PUBLIC_KEY);
        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        // when
        when(repository.findFirstByEmail(anyString())).thenReturn(Optional.of(person));
        when(mapper.toDto(any())).thenReturn(personDto);

        // then
        var actual = service.getByEmail(email);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(personDto);
    }

    @Test
    void getByEmail_throwException() {
        // given
        var email = "test";
        var person = easyRandom.nextObject(Person.class);
        person.setPublicKey(TEST_PUBLIC_KEY);
        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        // when
        when(repository.findFirstByEmail(anyString())).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> service.getByEmail(email))
                .isInstanceOf(PersonException.class)
                .hasMessage("Not found person with email: " + email);
    }

    @Test
    void getAll() {
        // given
        var person = easyRandom.nextObject(Person.class);
        person.setPublicKey(TEST_PUBLIC_KEY);
        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        // when
        when(repository.findAll()).thenReturn(List.of(person));
        when(mapper.toDtoList(anyList())).thenReturn(List.of(personDto));

        // then
        var actual = service.getAll();
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(List.of(personDto));

    }

    @Test
    void save() {
        // given
        var person = easyRandom.nextObject(Person.class);
        person.setPublicKey(TEST_PUBLIC_KEY);
        var personDto = easyRandom.nextObject(PersonDto.class);
        personDto.setPublicKey(TEST_PUBLIC_KEY);

        // when
        when(mapper.toEntity(any())).thenReturn(person);
        when(repository.save(any())).thenReturn(person);
        when(mapper.toDto(any())).thenReturn(personDto);

        // then
        var actual = service.save(personDto);
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(personDto);

    }
}