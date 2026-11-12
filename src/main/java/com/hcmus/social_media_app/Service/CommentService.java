package com.hcmus.social_media_app.Service;

import com.hcmus.social_media_app.Dto.PostCommentRequest;
import com.hcmus.social_media_app.Dto.PostCommentResponse;
import com.hcmus.social_media_app.Entity.Comment;
import com.hcmus.social_media_app.Entity.Post;
import com.hcmus.social_media_app.Entity.User;
import com.hcmus.social_media_app.Exception.ResourceNotFoundException;
import com.hcmus.social_media_app.Repository.CommentJpaRepository;
import com.hcmus.social_media_app.Repository.PostJpaRepository;
import com.hcmus.social_media_app.Repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentJpaRepository commentJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    @Transactional
    public PostCommentResponse postComment(Long postId, String email, PostCommentRequest req) {
        if (!postJpaRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Bài viết không tồn tại !!!");
        }
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại !!!"));

        Post postRef = postJpaRepository.getReferenceById(postId);
        Comment comment = new Comment(user, postRef, req.getContent());
        Comment savedComment = commentJpaRepository.save(comment);

        return PostCommentResponse.fromEntity(savedComment);
    }
    @Transactional(readOnly = true)
    public Page<PostCommentResponse> getAllCommentFromPost(Long postId, Pageable pageable) {
        if (!postJpaRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Bài viết không tồn tại !!!");
        }
        return commentJpaRepository.findByPostId(postId, pageable)
                .map(PostCommentResponse::fromEntity);
    }
}
