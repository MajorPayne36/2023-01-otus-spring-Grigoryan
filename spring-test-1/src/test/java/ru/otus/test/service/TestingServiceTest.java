package ru.otus.test.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.test.config.TestContextConfig;
import ru.otus.test.domain.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
class TestingServiceTest {
    private static final String PERSON_NAME = "Person Name";
    private static final String USER_INPUT_FILE = "user-input.txt";
    private static final String END_OF_TEST = String.format("Congratulations tester %s! Your bal %d", PERSON_NAME, 2);

    @Autowired
    private TestingService testingService;
    @Autowired
    private ByteArrayOutputStream byteArrayOutputStream;

    @BeforeAll
    static void redefineInputStreams() throws IOException {
        redefineInputStream();
    }

    @Test
    void startTesting() {
        // Вызываем регистрацию пользователя
        testingService.startTesting();
        Person person = testingService.getTester();

        // Проверяем на создание
        assertNotNull(person);
        assertEquals(PERSON_NAME, person.name());

        // Получаем внутренности вывода
        String output = byteArrayOutputStream.toString();
        List<String> stringList = Arrays.stream(output.split("\n")).toList();

        // Получаем последний вывод
        String lastOutput = stringList.get(stringList.size() - 1);
        assertEquals(END_OF_TEST, lastOutput);
    }

    private static void redefineInputStream() throws IOException {
        // Переопределяем стандартный ввод для симулирования ввода в консоль
        System.setIn(new ByteArrayInputStream(new ClassPathResource(USER_INPUT_FILE).getInputStream().readAllBytes()));
    }
}