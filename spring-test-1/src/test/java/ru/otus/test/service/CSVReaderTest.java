package ru.otus.test.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.test.config.TestContextConfig;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestContextConfig.class)
class CSVReaderTest {

    @Autowired
    CSVReader csvReader;


    @Test
    void getFieldsFromCsv() throws IOException {
        // Загружаем пропсы из тестового файла
        var props = PropertiesLoaderUtils.loadProperties(new ClassPathResource("/application-test.properties"));
        final String fileName = props.getProperty("tests.csv.file.name");
        final String separator = props.getProperty("tests.csv.separator.comma");

        // Тестируем
        List<List<String>> fields = csvReader.getFieldsFromCsv(fileName, separator);
        assertEquals("test question", fields.get(0).get(0));
        assertEquals("test answer", fields.get(0).get(1));
    }
}