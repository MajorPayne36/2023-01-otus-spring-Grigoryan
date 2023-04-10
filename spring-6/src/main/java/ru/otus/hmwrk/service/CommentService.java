package ru.otus.hmwrk.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hmwrk.dao.CommentsRepository;
import ru.otus.hmwrk.entity.Comment;
import ru.otus.hmwrk.exceptions.NotValidIdentifierException;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class CommentService implements CommonService<Comment, Long> {

    private final CommentsRepository commentsRepository;

    @Override
    public List<Comment> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(commentsRepository.findById(id));
    }

    @Override
    public Optional<Comment> save(Comment entity) {
        return Optional.ofNullable(commentsRepository.save(entity));
    }

    @Override
    public void updateNameById(Long id, String content) {
        if (nonNull(id)){
            var comment = findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Entity with id: " + id + "not found"));

            comment.setContent(content);
            save(comment);
        } else {
            throw new NotValidIdentifierException(id);
        }
    }
}
