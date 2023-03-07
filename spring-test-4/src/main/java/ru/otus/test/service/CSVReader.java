package ru.otus.test.service;

import ru.otus.test.exception.CsvReaderException;

import java.util.List;

public interface CSVReader {
    List<List<String>> getFieldsFromCsv(String fileName, String separator) throws CsvReaderException;
}
