package ru.otus.hmwrk.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import ru.otus.hmwrk.entity.Book;

import java.util.List;


/**
 * Класс сохраняющий сущности типа {@link Book}.
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class BooksRepository implements Repository<Book, Long> {

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
    public Book save(Book entity) {
        if (entity.getId() <= 0) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
