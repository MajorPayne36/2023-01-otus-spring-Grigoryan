package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.test.config.props.AppProps;
import ru.otus.test.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CSVServiceTest {
    public static final String QUESTION = "q";
    public static final String ANSWER = "a";
    @Mock
    private CSVReader csvReader;
    @Mock
    private AppProps appProps;
    @InjectMocks
    private CSVServiceImpl csvService;

    @Test
    void testGetQuestionsFromCSV_should_return_test() {
        // given
        List<List<String>> linesFromCsv = List.of(List.of(QUESTION, ANSWER));

        // when
        when(csvReader.getFieldsFromCsv(any(), any())).thenReturn(linesFromCsv);
        List<Question> questions = csvService.getQuestionsFromCSV();

        // then
        assertThat(questions.get(0).question()).isEqualTo(QUESTION);
        assertThat(questions.get(0).answer()).isEqualTo(ANSWER);
    }
}