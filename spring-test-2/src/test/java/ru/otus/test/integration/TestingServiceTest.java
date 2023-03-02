package ru.otus.test.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TestingServiceTest {
    private static final String PERSON_NAME = "Person Name";
    private static final String USER_INPUT_FILE = "user-input.txt";
    private static final String END_OF_TEST = String.format("Congratulations tester %s! Your bal %d", PERSON_NAME, 3);

    @Autowired
    private TestingService testingService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void registerTester() throws IOException {
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
        redefineInputStream();
        var currentPerson = new Person(PERSON_NAME);

        // Переопределяем стандартный вывод
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // Вызываем регистрацию пользователя
        testingService.registerTester(currentPerson);
        testingService.startTesting();

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