package ru.otus.test.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.test.config.props.AppProps;
import ru.otus.test.domain.Person;
import ru.otus.test.domain.Question;
import ru.otus.test.exception.MyRuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestingServiceImpl implements TestingService {
    private static final Logger logger = LoggerFactory.getLogger(TestingServiceImpl.class);

    private final AppProps appProps;
    private final MessageSource messageSource;
    private final CSVService csvService;
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private Person tester;

    @Override
    public Person getTester() {
        return this.tester;
    }

    private Person registerTester() {
        try {
            var greetings = messageSource.getMessage("testing.welcome", null, appProps.getLocale());
            logger.info(greetings);
            String name = br.readLine();
            return new Person(name);
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }

    @Override
    public void startTesting() {
        this.tester = registerTester();
        int bal = appProps.getStartBal();
        List<Question> questions = csvService.getQuestionsFromCSV();
        for (Question question :
                questions) {
            bal += askQuestion(question);
        }
        showResult(bal);
    }

    private int askQuestion(Question question) {
        try {
            logger.info("-----------------------------");
            logger.info(question.question());
            String currentAnswer = br.readLine();
            return question.answer().equals(currentAnswer) ? appProps.getCorrect() : appProps.getIncorrect();
        } catch (IOException e) {
            throw new MyRuntimeException(e.getMessage());
        }
    }

    private void showResult(int bal) {
        var congratulation = messageSource.getMessage("testing.congratulation", new Object[]{tester.name(), bal}, appProps.getLocale());
        var tryAgain = messageSource.getMessage("testing.try-again", null, appProps.getLocale());
        if (bal >= appProps.getThreshold()) {
            logger.info(congratulation);
        } else {
            logger.info(tryAgain);
        }
    }
}
