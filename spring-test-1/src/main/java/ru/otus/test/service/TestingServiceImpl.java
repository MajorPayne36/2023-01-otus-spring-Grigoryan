package ru.otus.test.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
//@PropertySource("classpath:application.properties")
public class TestingServiceImpl implements TestingService {
    private static final String GREETINGS = "Hello to testing. Enter your name";
    @Value("${tests.testing.bal.correct}")
    private int correctBal;
    @Value("${tests.testing.bal.incorrect}")
    private int incorrectBal;
    @Value("${tests.testing.bal.start}")
    private int startBal;
    @Value("${tests.testing.bal.threshold}")
    private int thresholdBal;

    private final CSVService csvService;
    private final BufferedReader br;
    private Person tester;

    public TestingServiceImpl(CSVService csvService) {
        this.csvService = csvService;
        this.br = new BufferedReader(new InputStreamReader(System.in));
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
            System.out.printf("Congratulations tester %s! Your bal %d", tester.name(), bal);
        } else {
            System.out.println("You haven't collected enough balls. Try again.");
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
