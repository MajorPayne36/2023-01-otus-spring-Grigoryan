package ru.otus.test.config.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Data
@ConfigurationProperties(prefix = "application")
public class AppProps {
    private Locale locale;

    private String separator;
    private String testFile;
    private Integer correct;
    private Integer incorrect;
    private Integer startBal;
    private Integer threshold;
//    @Data
//    static class TestingProps {
//        private String testFile;
//        private Integer correct;
//        private Integer incorrect;
//        private Integer startBal;
//        private Integer threshold;
//    }
//
//    @Data
//    static class FileProps {
//        private String separator;
//    }

}
