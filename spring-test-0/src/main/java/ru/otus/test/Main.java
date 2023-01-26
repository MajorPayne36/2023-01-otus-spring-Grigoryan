package ru.otus.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.test.service.TestingService;

public class Main {

    public static void main(String[] args) {
        System.out.println("*************************************");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        TestingService testingService = context.getBean(TestingService.class);
        testingService.startTesting();
    }
}
