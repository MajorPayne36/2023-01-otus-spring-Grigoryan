package ru.otus.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.testing")
public record TestingProps(String testFile, Integer correct, Integer incorrect, Integer startBal, Integer threshold) {
}
