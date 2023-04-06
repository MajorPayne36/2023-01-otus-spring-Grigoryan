package ru.otus.hmwrk.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hmwrk.entity.Comment;
import ru.otus.hmwrk.exceptions.NotValidIdentifierException;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class CommentsRepository implements Repository<Comment, Long>{

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Comment> findAll() {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c join fetch c.book", Comment.class);
        return query.getResultList();
    }

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findByName(String content) {
        TypedQuery<Comment> query = em.createQuery("select c " +
                        "from Comment c " +
                        "where c.content = :content ",
                Comment.class);
        query.setParameter("content", content);
        return query.getResultList();
    }

    @Override
    public void updateNameById(Long id, String content) {
        if (nonNull(id)){
            var comment = Optional.ofNullable(findById(id))
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));

            comment.setContent(content);
            em.merge(comment);
        } else {
            throw new NotValidIdentifierException(id);
        }
    }

    @Override
    public Comment save(Comment entity) {
        if (entity.getId() <= 0) {
            em.persist(entity);
            return entity;
        } else {
            return em.merge(entity);
        }
    }
}
