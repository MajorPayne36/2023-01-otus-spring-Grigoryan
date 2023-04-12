package ru.otus.hmwrk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hmwrk.entity.Comment;

import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByContent(String content);
}
