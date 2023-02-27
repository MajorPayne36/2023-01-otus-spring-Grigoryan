package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.test.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CSVServiceTest {
    @Autowired
    private CSVService csvService;

    @Test
    void getQuestionsFromCSV_should_return_test() {
        List<Question> questions = csvService.getQuestionsFromCSV();
        assertThat(questions.get(0).question()).isEqualTo("what framework i use in this project?");
        assertThat(questions.get(0).answer()).isEqualTo("spring");
    }
}