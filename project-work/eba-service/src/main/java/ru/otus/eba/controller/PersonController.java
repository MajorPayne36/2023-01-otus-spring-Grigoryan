package ru.otus.eba.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.eba.controller.openapi.PersonApi;
import ru.otus.eba.dto.openapi.PersonDto;
import ru.otus.eba.service.PersonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {
    private final PersonService personService;

    @Override
    public ResponseEntity<List<PersonDto>> getAll() {
        return ResponseEntity.ok(personService.getAll());
    }

    @Override
    public ResponseEntity<PersonDto> savePerson(PersonDto personDto) {
        return ResponseEntity.ok(personService.save(personDto));
    }
}
