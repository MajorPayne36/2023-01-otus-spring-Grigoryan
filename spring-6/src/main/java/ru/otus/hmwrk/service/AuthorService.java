package ru.otus.hmwrk.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.AuthorsRepository;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.exceptions.AuthorNameNotValidException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorService implements CommonService<Author, Long> {

    private final AuthorsRepository authorsRepository;

    @Override
    public List<Author> findAll() {
        return authorsRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(authorsRepository.findById(id));
    }

    @Override
    public Optional<Author> save(Author entity) {
        return Optional.ofNullable(authorsRepository.save(entity));
    }

    @Override
    public void updateNameById(Long id, String name) {
        var names = name.split(" ");
        if (names.length == 2) {
            var author = findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));
            author.setFirstName(names[0]);
            author.setLastName(names[1]);
            save(author);
        } else {
            throw new AuthorNameNotValidException(name);
        }
    }
}