package ru.otus.hmwrk.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenresRepository implements Repository<Genre, Long> {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        EntityGraph<?> entityGraph = em.getEntityGraph("genres-entity-graph");
        String jpql = "select g from Genre g";
        TypedQuery<Genre> query = em.createQuery(jpql, Genre.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Genre findById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> findByName(String name) {
        TypedQuery<Genre> query = em.createQuery("select g " +
                        "from Genre g " +
                        "where g.name = :name",
                Genre.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public Genre save(Genre entity) {
        if (entity.getId() <= 0) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
