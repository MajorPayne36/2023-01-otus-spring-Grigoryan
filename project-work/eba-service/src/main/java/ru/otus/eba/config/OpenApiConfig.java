package ru.otus.eba.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.TreeMap;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        String version = getClass().getPackage().getImplementationVersion();
        return new OpenAPI()
                .info(new Info()
                        .title("Единая безопасная авторизация")
                        .version(version)
                        .contact(new Contact().name("Григорян Андраник").email("agrigya@ya.ru").url("https://t.me/grigland1")));
    }

    @Bean
    public OpenApiCustomiser sortSchemasAlphabetically() {
        return openApi -> {
            openApi.getComponents().setSchemas(
                    new TreeMap<>(openApi.getComponents().getSchemas()));

            List<Tag> tags = openApi.getTags().stream().sorted((tag1, tag2) ->
                            StringUtils.compare(tag1.getName(), tag2.getName()))
                    .toList();
            openApi.setTags(tags);
        };
    }
}
