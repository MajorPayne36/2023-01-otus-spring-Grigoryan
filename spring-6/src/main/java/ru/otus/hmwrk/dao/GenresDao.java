package ru.otus.hmwrk.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Genre;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GenresDao implements Dao<Genre, Long> {

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
    public void updateNameById(Long id, String name) {
        Query query = em.createQuery("update Genre g " +
                "set g.name = :name " +
                "where g.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
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
