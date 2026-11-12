package com.hcmus.social_media_app.Repository;

import com.hcmus.social_media_app.Entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentJpaRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"user"})
    Page<Comment> findByPostId(Long postId, Pageable pageable);
}
