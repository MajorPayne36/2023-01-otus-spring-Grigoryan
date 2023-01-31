package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.test.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CSVServiceTest {

    @Mock
    private CSVReaderImpl csvReader;

    @InjectMocks
    private CSVServiceImpl csvService;

    @Test
    void getQuestionsFromCSV_shouldReturn_test() throws IOException {
        var csvReaderData = new ArrayList<List<String>>();
        csvReaderData.add(List.of("question", "answer"));

        doReturn(csvReaderData).when(csvReader).getFieldsFromCsv(any(), any());

        List<Question> questions = csvService.getQuestionsFromCSV();
        assertEquals("question", questions.get(0).question());
        assertEquals("answer", questions.get(0).answer());

    }
}