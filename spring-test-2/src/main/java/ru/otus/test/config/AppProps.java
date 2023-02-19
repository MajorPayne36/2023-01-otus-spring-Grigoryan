package ru.otus.test.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public record AppProps(FileProps fileProps, TestingProps testingProps, Locale locale) {

}
