package ru.otus.test.dao;

import ru.otus.test.domain.Person;

public interface TestRepository {
    void saveTestResult(Person person, int bal);
}
