package ru.otus.test.dao;

import ru.otus.test.domain.Person;

public class TestRepositoryImpl implements TestRepository {
    @Override
    public void saveTestResult(Person person, int bal) {
        System.out.printf("saving tester %s with bal %d%n", person.name(), bal);
    }
}
