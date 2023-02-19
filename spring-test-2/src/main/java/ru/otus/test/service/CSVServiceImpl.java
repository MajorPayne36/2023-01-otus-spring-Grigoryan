package ru.otus.test.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.test.config.AppProps;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CSVServiceImpl implements CSVService {

    private final CSVReader csvReader;
    private final AppProps appProps;

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
        return csvReader.getFieldsFromCsv(appProps.testingProps().testFile(), appProps.fileProps().separator());
    }
}
