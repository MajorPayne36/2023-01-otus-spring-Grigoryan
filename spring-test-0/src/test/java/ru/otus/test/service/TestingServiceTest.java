package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import ru.otus.test.dao.TestRepositoryImpl;
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
    private static final String USER_INPUT_FILE = "user-input.txt";
    private static final String END_OF_TEST = String.format("Saving tester %s with bal %d to db", PERSON_NAME, 2);

    @Test
    void registerTester() throws IOException {
        redefineInputStream();

        // Создаем новый класс, где будет использоваться переопределяемый ввод
        TestingService service = new TestingServiceImpl(new TestRepositoryImpl(), new CSVServiceImpl(new CSVReaderImpl()));

        // Вызываем регистрацию пользователя
        Person person = service.getTester();

        // Проверяем на создание
        assertNotNull(person);
        assertEquals(PERSON_NAME, person.name());
    }

    @Test
    void startTesting() throws IOException {
        redefineInputStream();

        // Переопределяем стандартный вывод
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(byteArrayOutputStream));

        // Создаем новый класс, где будет использоваться переопределяемый ввод
        TestingService service = new TestingServiceImpl(new TestRepositoryImpl(), new CSVServiceImpl(new CSVReaderImpl()));

        // Вызываем регистрацию пользователя
        service.startTesting();

        // Получаем внутренности вывода
        String output = byteArrayOutputStream.toString();
        List<String> stringList = Arrays.stream(output.split("\n")).toList();
        // Получаем последний вывод
        String lastOutput = stringList.get(stringList.size() - 1);
        assertEquals(END_OF_TEST, lastOutput);
    }

    private void redefineInputStream() throws IOException {
        // Переопределяем стандартный ввод для симулирования ввода в консоль
        System.setIn(new ByteArrayInputStream(new ClassPathResource(USER_INPUT_FILE).getInputStream().readAllBytes()));

    }
}