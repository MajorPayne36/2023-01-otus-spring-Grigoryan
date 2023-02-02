package ru.otus.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
public class TestingServiceImpl implements TestingService {
    private static final String GREETINGS = "Hello to testing. Enter your name";
    private static final String CONGRATULATIONS = "Congratulations tester %s! Your bal %d";
    private static final String TRY_AGAIN = "You haven't collected enough balls. Try again.";
    private final CSVService csvService;
    private final BufferedReader br;
    @Value("${tests.testing.bal.correct}")
    private int correctBal;
    @Value("${tests.testing.bal.incorrect}")
    private int incorrectBal;
    @Value("${tests.testing.bal.start}")
    private int startBal;
    @Value("${tests.testing.bal.threshold}")
    private int thresholdBal;
    private Person tester;

    public TestingServiceImpl(CSVService csvService, BufferedReader bufferedReader) {
        this.csvService = csvService;
        this.br = bufferedReader;
    }

    @Override
    public Person getTester() {
        return this.tester;
    }

    @Override
    public void startTesting() {
        this.tester = registerTester();
        int bal = startBal;
        List<Question> questions = csvService.getQuestionsFromCSV();
        for (Question question :
                questions) {
            bal += askQuestion(question);
        }
        showResult(bal);
    }

    private void showResult(int bal) {
        if (bal >= thresholdBal) {
            System.out.printf(CONGRATULATIONS, tester.name(), bal);
        } else {
            System.out.println(TRY_AGAIN);
        }
    }

    private Person registerTester() {
        try {
            System.out.println(GREETINGS);
            String name = br.readLine();
            return new Person(name);
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }

    private int askQuestion(Question question) {
        try {
            System.out.println("-----------------------------");
            System.out.println(question.question());
            String currentAnswer = br.readLine();
            return question.answer().equals(currentAnswer) ? correctBal : incorrectBal;
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }
}
