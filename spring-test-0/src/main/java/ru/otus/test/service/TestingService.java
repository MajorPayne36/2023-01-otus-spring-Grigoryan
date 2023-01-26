package ru.otus.test.service;

import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;

public interface TestingService {
    Person registerTester();

    int askQuestion(Question question);

    void startTesting();
}
