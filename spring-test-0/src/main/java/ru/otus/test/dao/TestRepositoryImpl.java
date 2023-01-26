package ru.otus.test.dao;

import ru.otus.test.domain.Person;

public class TestRepositoryImpl implements TestRepository {
    @Override
    public void saveTestResult(Person person, int bal) {
        System.out.printf("Saving tester %s with bal %d to db", person.name(), bal);
    }
}
