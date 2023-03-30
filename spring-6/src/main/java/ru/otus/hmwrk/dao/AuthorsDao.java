package ru.otus.hmwrk.dao;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Author;
import ru.otus.hmwrk.exceptions.AuthorNameNotValidException;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AuthorsDao implements Dao<Author, Long> {

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
            Query query = em.createQuery("update Author a  " +
                    "set a.firstName = :firstName, " +
                    " a.lastName = :lastName " +
                    "where a.id = :id");
            query.setParameter("firstName", names[0]);
            query.setParameter("lastName", names[1]);
            query.executeUpdate();
        } else {
            throw new AuthorNameNotValidException(name);
        }
    }

//    @Override
//    public Author save(Author entity) {
//        var query = em.createQuery("update Author a " +
//                "set a.firstName = :firstName, " +
//                " a.lastName = :lastName, " +
//                " a.birthday = :birthday, " +
//                " a.books = :books " +
//                "where a.id = :id");
//        query.setParameter("id", entity.getId());
//        query.setParameter("firstName", entity.getFirstName());
//        query.setParameter("lastName", entity.getLastName());
//        query.setParameter("birthday", entity.getBirthday());
//        if (entity.getBooks() == null) {
//            query.setParameter("books", entity.getBooks());
//        }
//        var count = query.executeUpdate();
//        return count > 0 ? entity : null;
//
//    }

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
