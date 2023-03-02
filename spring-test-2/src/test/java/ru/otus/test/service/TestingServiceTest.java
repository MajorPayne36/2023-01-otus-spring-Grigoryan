package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.test.config.props.AppProps;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TestingServiceTest {
    private static final String PERSON_NAME = "Person Name";
    private static final String END_OF_TEST = String.format("Congratulations tester %s! Your bal %d", PERSON_NAME, 3);
    public static final String ANSWER = "answer";
    public static final String QUESTION = "question";

    @Mock
    private AppProps appProps;
    @Mock
    private MessageSource messageSource;
    @Mock
    private CSVService csvService;
    @Mock
    private BufferedReader br;

    @InjectMocks
    private TestingServiceImpl testingService;

    @Captor
    private ArgumentCaptor<Object[]> message;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void registerTester() {
        // given
        var currentPerson = new Person(PERSON_NAME);

        // when
        testingService.registerTester(currentPerson);
        var resultPerson = testingService.getTester();

        // then
        assertThat(resultPerson).usingRecursiveComparison()
                .isEqualTo(currentPerson);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void startTesting() throws IOException {
        // given
        setAppPropsToService();
        var currentPerson = new Person(PERSON_NAME);
        var question = List.of(new Question(QUESTION, ANSWER));
        var personNameAndBal = new Object[]{currentPerson.name(), 1};

        // when
        when(csvService.getQuestionsFromCSV()).thenReturn(question);
        when(br.readLine()).thenReturn(ANSWER);
        when(messageSource.getMessage(any(), any(), any())).thenReturn(END_OF_TEST);

        testingService.registerTester(currentPerson);
        testingService.startTesting();

        // then
        verify(messageSource).getMessage(eq("testing.congratulation"), message.capture(), any());
        assertThat(message.getValue())
                .isEqualTo(personNameAndBal);
    }

    private void setAppPropsToService() {
        var appProps = new AppProps();
        appProps.setCorrect(1);
        appProps.setIncorrect(0);
        appProps.setLocale(Locale.ENGLISH);
        appProps.setStartBal(0);
        appProps.setThreshold(2);

        ReflectionTestUtils.setField(testingService, "appProps", appProps);
        ReflectionTestUtils.setField(testingService, "br", br);
    }
}