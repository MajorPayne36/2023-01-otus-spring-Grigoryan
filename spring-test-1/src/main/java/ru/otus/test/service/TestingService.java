package ru.otus.test.service;

import ru.otus.test.domain.Person;

public interface TestingService {
    Person getTester();

    void startTesting();
}
