package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.repository.CommentsRepository;
import ru.otus.hmwrk.entity.Comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CommentService implements CrudService<Comment, Long> {

    private final CommentsRepository commentsRepository;

    @Override
    public List<Comment> findAll() {
        return StreamSupport.stream(commentsRepository.findAll().spliterator(), false).toList();
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
