package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.CommentsRepository;
import ru.otus.hmwrk.domain.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CommentService implements CrudService<Comment, Long> {

    private final CommentsRepository commentsRepository;

    @Override
    public void deleteById(Long id) {
        commentsRepository.deleteById(id);
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return commentsRepository.findById(id);
    }

    @Override
    public List<Comment> findAll() {
        return commentsRepository.findAll();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentsRepository.findById(id);
    }

    @Override
    public Optional<Comment> save(Comment entity) {
        return Optional.of(commentsRepository.save(entity));
    }
}
