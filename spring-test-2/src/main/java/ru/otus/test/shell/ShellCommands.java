package ru.otus.test.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.test.domain.Person;
import ru.otus.test.service.TestingService;

@ShellComponent
@RequiredArgsConstructor
public class ShellCommands {
    private final TestingService testingService;

    @ShellMethod(value = "Registration", key = {"r", "register"})
    public String register(@ShellOption String name) {
        testingService.registerTester(new Person(name));
        return String.format("Registered tester: %s", name);
    }

    @ShellMethod(value = "Testing", key = {"start", "test"})
    @ShellMethodAvailability(value = "isStartTestingEventCommandAvailable")
    public void startTest() {
        testingService.startTesting();
    }

    private Availability isStartTestingEventCommandAvailable(){
        return testingService.getTester() == null? Availability.unavailable("You don't registered"): Availability.available();
    }
}
