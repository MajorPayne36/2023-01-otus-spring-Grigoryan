package ru.otus.hmwrk.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hmwrk.entity.Author;

import java.util.List;

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
    public Author save(Author entity) {
        if (entity.getId() == null) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
