package ru.otus.hmwrk.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Book;

import java.util.List;


/**
 * Класс сохраняющий сущности типа {@link Book}.
 */
@Log4j2
@Repository
@RequiredArgsConstructor
public class BooksDao implements Dao<Book, Long> {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findAll() {
        String jpqlAuthors = "select b from Book b ";
        return em.createQuery(jpqlAuthors, Book.class).getResultList();
    }

    @Override
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public List<Book> findByName(String name) {
        TypedQuery<Book> query = em.createQuery("select b " +
                        "from Book b " +
                        "where b.name = :name",
                Book.class);
        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public void updateNameById(Long id, String name) {
        Query query = em.createQuery("update Book b " +
                "set b.name = :name " +
                "where b.id = :id");
        query.setParameter("name", name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Book save(Book entity) {
        if (entity.getId() <= 0) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
