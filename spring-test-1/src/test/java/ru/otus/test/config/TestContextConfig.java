package ru.otus.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
@ComponentScan("ru.otus.test")
@TestPropertySource("classpath:application-test.properties")
public class TestContextConfig {

    public static final String USER_INPUT_TXT = "user-input.txt";
    public static final String APPLICATION_TEST_PROPERTIES = "application-test.properties";

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocations(new ClassPathResource(APPLICATION_TEST_PROPERTIES));
        return pspc;
    }

    @Bean
    public ByteArrayInputStream byteArrayInputStream() throws IOException {
        return new ByteArrayInputStream(new ClassPathResource(USER_INPUT_TXT).getInputStream().readAllBytes());
    }

    @Bean
    public BufferedReader bufferedReader() throws IOException {
        return new BufferedReader(new InputStreamReader(byteArrayInputStream()));
    }
}
