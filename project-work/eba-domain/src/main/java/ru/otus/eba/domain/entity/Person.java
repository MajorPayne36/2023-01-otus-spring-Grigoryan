package ru.otus.eba.domain.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document("person")
@Accessors(chain = true)
public class Person {
    @Id
    private String id;
    private String lastName;
    private String firstName;
    private String nickName;
    private LocalDate birthday;
    private String email;
    private String publicKey;
}
