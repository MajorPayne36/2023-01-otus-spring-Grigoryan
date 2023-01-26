package ru.otus.test.service;

import ru.otus.test.dao.TestRepository;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestingServiceImpl implements TestingService {
    public static final String GREETINGS = "Hello to testing. Enter your name";
    private final int CORRECT = 1;
    private static final int INCORRECT = 0;
    public static final int START_BAL = 0;

    private final Person tester;
    private final TestRepository testRepository;
    private final CSVService csvService;

    public TestingServiceImpl(TestRepository testRepository, CSVService csvService) {
        this.testRepository = testRepository;
        this.csvService = csvService;
        this.tester = registerTester();
    }

    @Override
    public Person registerTester() {

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println(GREETINGS);
            String name = br.readLine();
            return new Person(name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public int askQuestion(Question question) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("-----------------------------");
            System.out.println(question.question());
            String currentAnswer = br.readLine();
            return question.answer().equals(currentAnswer) ? CORRECT : INCORRECT;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startTesting() {
        int bal = START_BAL;
        List<Question> questions = csvService.getQuestionsFromCSV();
        for (Question question :
                questions) {
            bal += askQuestion(question);
        }
        testRepository.saveTestResult(this.tester, bal);
    }
}
