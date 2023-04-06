package ru.otus.hmwrk.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.exceptions.AuthorNameNotValidException;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorsRepository implements Repository<Author, Long> {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("authors-entity-graph");
        TypedQuery<Author> query = em.createQuery("select a from Author a", Author.class);
        query.setHint("jakarta.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Author findById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> findByName(String firstName) {
        TypedQuery<Author> query = em.createQuery("select a " +
                        "from Author a " +
                        "where a.firstName = :firstName ",
                Author.class);
        query.setParameter("firstName", firstName);
        return query.getResultList();
    }

    @Override
    public void updateNameById(Long id, String name) {
        var names = name.split(" ");
        if (names.length == 2) {
            var author = Optional.ofNullable(findById(id))
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));
            author.setFirstName(names[0]);
            author.setLastName(names[1]);
            em.merge(author);
        } else {
            throw new AuthorNameNotValidException(name);
        }
    }

    @Override
    public Author save(Author entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
