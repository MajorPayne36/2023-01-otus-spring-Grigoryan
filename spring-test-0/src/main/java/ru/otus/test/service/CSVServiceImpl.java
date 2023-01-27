package ru.otus.test.service;

import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.IOException;
import java.util.List;

public class CSVServiceImpl implements CSVService {
    private static final String COMMA = ",";
    private static final String TEST_FILE = "test.csv";

    private final CSVReader csvReader;

    public CSVServiceImpl(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public List<Question> getQuestionsFromCSV() {
        try {
            return getFieldsFromCsvByLambda()
                    .stream()
                    .map(list -> new Question(list.get(0), list.get(1)))
                    .toList();
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }

    private List<List<String>> getFieldsFromCsvByLambda() throws IOException {
        return csvReader.getFieldsFromCsv(TEST_FILE, COMMA);
    }
}
