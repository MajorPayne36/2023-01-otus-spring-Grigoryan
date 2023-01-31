package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CSVReaderTest {

    CSVReader csvReader = new CSVReaderImpl();

    @Test
    void getFieldsFromCsv() throws IOException {
        // Загружаем пропсы из тестового файла
        var props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("application-test.properties"));
        final String fileName = props.getProperty("tests.csv.file.name");
        final String separator = props.getProperty("tests.csv.separator.comma");

        // Тестируем
        List<List<String>> fields = csvReader.getFieldsFromCsv(fileName, separator);
        assertEquals("test question", fields.get(0).get(0));
        assertEquals("test answer", fields.get(0).get(1));
    }
}