package ru.otus.test.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import ru.otus.test.exception.CsvReaderException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Component
public class CSVReaderImpl implements CSVReader {
    @Override
    public List<List<String>> getFieldsFromCsv(String fileName, String separator) throws CsvReaderException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()));
        } catch (IOException e) {
            throw new CsvReaderException(e.getMessage());
        }
        return br.lines()
                .map(line -> Arrays.asList(line.split(separator)))
                .toList();
    }
}
