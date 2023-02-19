package ru.otus.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application.file")
public record FileProps(String separator) {
}
