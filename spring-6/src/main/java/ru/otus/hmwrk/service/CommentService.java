package ru.otus.hmwrk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hmwrk.dao.CommentsRepository;
import ru.otus.hmwrk.entity.Comment;

import java.util.List;
import java.util.Optional;

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
}
