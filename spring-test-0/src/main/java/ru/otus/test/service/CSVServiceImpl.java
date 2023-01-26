package ru.otus.test.service;

import org.springframework.core.io.ClassPathResource;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class CSVServiceImpl implements CSVService {
    private static final String COMMA = ",";
    private static final String TEST_FILE = "test.csv";

    @Override
    public List<List<String>> getFieldsFromCsvByLambda() throws IOException {
        List<List<String>> result = Files.readAllLines(new ClassPathResource(TEST_FILE).getFile().toPath())
                .stream()
                .map(line -> Arrays.asList(line.split(COMMA)))
                .toList();

        System.out.println(result);

        return result;
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
}
