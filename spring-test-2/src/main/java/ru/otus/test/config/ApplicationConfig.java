package ru.otus.test.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.otus.test.config.props.AppProps;

@Configuration
@EnableConfigurationProperties({AppProps.class})
public class ApplicationConfig {

}
