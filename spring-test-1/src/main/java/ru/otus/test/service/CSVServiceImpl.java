package ru.otus.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.IOException;
import java.util.List;

@Service
public class CSVServiceImpl implements CSVService {

    private final CSVReader csvReader;
    @Value("${tests.csv.separator.comma}")
    private String comma;
    @Value("${tests.csv.file.name}")
    private String testFile;


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
        return csvReader.getFieldsFromCsv(testFile, comma);
    }
}
