package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.test.config.TestContextConfig;
import ru.otus.test.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
class CSVServiceTest {

    @Spy
    private CSVReaderImpl csvReader;

    @InjectMocks
    private CSVServiceImpl csvService;

    @Test
    void getQuestionsFromCSV_shouldReturn_test() throws IOException, IllegalAccessException, NoSuchFieldException {
        // Меняем поля
        changeValues();

        List<Question> questions = csvService.getQuestionsFromCSV();
        assertEquals("test question", questions.get(0).question());
        assertEquals("test answer", questions.get(0).answer());
    }

    @Test
    void df() throws IOException {
        var csvReaderData = new ArrayList<List<String>>();
        csvReaderData.add(List.of("question", "answer"));

        doReturn(csvReaderData).when(csvReader).getFieldsFromCsv(any(), any());

        List<Question> questions = csvService.getQuestionsFromCSV();
        assertEquals("question", questions.get(0).question());
        assertEquals("answer", questions.get(0).answer());

    }

    private void changeValues() {
        ReflectionTestUtils.setField(csvService, "comma", ",");
        ReflectionTestUtils.setField(csvService, "testFile", "test.csv");
    }
}