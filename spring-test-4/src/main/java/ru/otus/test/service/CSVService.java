package ru.otus.test.service;

import ru.otus.test.domain.Question;

import java.util.List;

public interface CSVService {

    List<Question> getQuestionsFromCSV();
}
