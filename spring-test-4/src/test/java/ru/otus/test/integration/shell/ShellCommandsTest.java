package ru.otus.test.integration.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.shell.InputProvider;
import org.springframework.shell.ResultHandlerService;
import org.springframework.shell.Shell;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.util.ReflectionTestUtils;
import ru.otus.test.domain.Person;
import ru.otus.test.service.TestingService;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShellCommandsTest {

    private static final String PERSON_NAME = "Vasilih";
    private static final String USER_INPUT_FILE = "user-input.txt";
    private static final String END_OF_TEST = String.format("Congratulations tester %s! Your bal %d", PERSON_NAME, 3);

    private static final String GREETING_PATTERN = "Registered tester: %s";
    private static final String COMMAND_LOGIN = "register";
    private static final String COMMAND_LOGIN_SHORT = "r";
    private static final String COMMAND_START_TESTING = "start";
    private static final String COMMAND_LOGIN_PATTERN = "%s %s";
    @SpyBean
    private ResultHandlerService resultHandlerService;

    @Autowired
    private TestingService testingService;
    @Autowired
    private Shell shell;

    private InputProvider inputProvider;
    private ArgumentCaptor<Object> argumentCaptor;

    @BeforeEach
    void setUp(){
        inputProvider = mock(InputProvider.class);
        argumentCaptor = ArgumentCaptor.forClass(Object.class);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void register() throws Exception {
        // given
        var currentPerson = new Person(PERSON_NAME);

        // when
        when(inputProvider.readInput())
                .thenReturn(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN, PERSON_NAME))
                .thenReturn(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN_SHORT, PERSON_NAME))
                .thenReturn(null);

        shell.run(inputProvider);
        var resultPerson = testingService.getTester();

        // then
        verify(resultHandlerService, times(2)).handle(argumentCaptor.capture());
        List<Object> results = argumentCaptor.getAllValues();
        assertThat(results).containsExactlyInAnyOrder(String.format(GREETING_PATTERN, PERSON_NAME),
                String.format(GREETING_PATTERN, PERSON_NAME));
        assertThat(resultPerson).usingRecursiveComparison()
                .isEqualTo(currentPerson);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void startTesting() throws Exception {
        // given
        redefineInputStream();

        // Переопределяем стандартный вывод
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // when
        when(inputProvider.readInput())
                .thenReturn(() -> String.format(COMMAND_LOGIN_PATTERN, COMMAND_LOGIN, PERSON_NAME))
                .thenReturn(() -> COMMAND_START_TESTING)
                .thenReturn(null);

        shell.run(inputProvider);

        // then
        verify(resultHandlerService, times(2)).handle(argumentCaptor.capture());
        // Получаем внутренности вывода
        String output = byteArrayOutputStream.toString();
        List<String> stringList = Arrays.stream(output.split("\n")).toList();
        // Получаем последний вывод
        String lastOutput = stringList.get(stringList.size() - 1);
        assertTrue(lastOutput.contains(END_OF_TEST));
    }

    private void redefineInputStream() throws IOException {
        // Переопределяем стандартный ввод для симулирования ввода в консоль
        System.setIn(new ByteArrayInputStream(new ClassPathResource(USER_INPUT_FILE).getInputStream().readAllBytes()));
        var changedBr = new BufferedReader(new InputStreamReader(System.in));
        ReflectionTestUtils.setField(testingService, "br", changedBr);
    }
}