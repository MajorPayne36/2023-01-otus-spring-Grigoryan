package ru.otus.test.service;

import java.io.IOException;
import java.util.List;

public interface CSVReader {
    List<List<String>> getFieldsFromCsv(String fileName, String separator) throws IOException;
}
