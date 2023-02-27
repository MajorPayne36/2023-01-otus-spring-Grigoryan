//package ru.otus.test.startup;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import ru.otus.test.service.TestingService;
//
//@RequiredArgsConstructor
//public class AfterStartupRunner {
//    private final TestingService testingService;
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void runAfterStartup(){
//        testingService.startTesting();
//    }
//}
