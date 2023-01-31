package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.otus.test.Main;
import ru.otus.test.domain.Person;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestingServiceTest {
    private static final String PERSON_NAME = "Person Name";
    public static final String USER_INPUT_TXT = "user-input.txt";
    private static final String END_OF_TEST = String.format("Congratulations tester %s! Your bal %d", PERSON_NAME, 2);

    @Test
    void integrateTest_of_testService() throws IOException {
        // Переопределяем ввод
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new ClassPathResource(USER_INPUT_TXT).getInputStream().readAllBytes());
        System.setIn(byteArrayInputStream);

        // Достаем бин из контекста
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        final TestingService testingService = context.getBean(TestingServiceImpl.class);

        // Переопределяем вывод для сравнения результатов
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

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
}