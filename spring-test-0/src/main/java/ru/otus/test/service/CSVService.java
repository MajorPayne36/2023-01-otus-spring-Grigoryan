package ru.otus.test.service;

import ru.otus.test.domain.Question;

import java.io.IOException;
import java.util.List;

public interface CSVService {
    List<List<String>> getFieldsFromCsvByLambda() throws IOException;

    List<Question> getQuestionsFromCSV();
}
