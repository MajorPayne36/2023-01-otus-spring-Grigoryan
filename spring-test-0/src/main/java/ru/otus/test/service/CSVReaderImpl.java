package ru.otus.test.service;

import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class CSVReaderImpl implements CSVReader{
    @Override
    public List<List<String>> getFieldsFromCsv(String fileName, String separator) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(fileName).getInputStream()));
        return br.lines()
                .map(line -> Arrays.asList(line.split(separator)))
                .toList();
    }
}
