package ru.otus.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class AppPropsConfig {

    public static final String PROPS_FILE = "application.properties";

    @Bean
    public static PropertySourcesPlaceholderConfigurer configurer() {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setLocations(new ClassPathResource(PROPS_FILE));
        return pspc;
    }

    @Bean
    public BufferedReader bufferedReader() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
