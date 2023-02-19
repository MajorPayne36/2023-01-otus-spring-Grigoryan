package ru.otus.test.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.test.config.AppProps;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private static final Logger logger = LoggerFactory.getLogger(TestingServiceImpl.class);

    private final AppProps appProps;
    private final MessageSource messageSource;
    private final CSVService csvService;
    private final BufferedReader br;
    private Person tester;

    @Override
    public Person getTester() {
        return this.tester;
    }

    @Override
    public void startTesting() {
        this.tester = registerTester();
        int bal = appProps.testingProps().startBal();
        List<Question> questions = csvService.getQuestionsFromCSV();
        for (Question question :
                questions) {
            bal += askQuestion(question);
        }
        showResult(bal);
    }

    private void showResult(int bal) {
        var congratulation = messageSource.getMessage("testing.congratulation", new Object[]{tester.name(), bal}, appProps.locale());
        var tryAgain = messageSource.getMessage("testing.try-again", null, appProps.locale());
        if (bal >= appProps.testingProps().threshold()) {
            logger.info(congratulation);
        } else {
            logger.info(tryAgain);
        }
    }

    private Person registerTester() {
        try {
            var greetings = messageSource.getMessage("testing.welcome", null, appProps.locale());
            logger.info(greetings);
            String name = br.readLine();
            return new Person(name);
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }

    private int askQuestion(Question question) {
        try {
            logger.info("-----------------------------");
            logger.info(question.question());
            String currentAnswer = br.readLine();
            return question.answer().equals(currentAnswer) ? appProps.testingProps().correct() : appProps.testingProps().incorrect();
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }
}
