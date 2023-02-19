package ru.otus.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.test.service.TestingService;

@SpringBootApplication
public class SpringTestApplication {
    public static void main(String[] args) {
        var context = SpringApplication.run(SpringTestApplication.class, args);
        var testingService = context.getBean(TestingService.class);
        testingService.startTesting();
    }
}
