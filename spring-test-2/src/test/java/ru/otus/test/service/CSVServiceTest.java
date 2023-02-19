package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.test.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CSVServiceTest {
    @Autowired
    private CSVService csvService;

    @Test
    void getQuestionsFromCSV_should_return_test() {
        List<Question> questions = csvService.getQuestionsFromCSV();
        assertEquals("test question", questions.get(0).question());
        assertEquals("test answer", questions.get(0).answer());
    }
}