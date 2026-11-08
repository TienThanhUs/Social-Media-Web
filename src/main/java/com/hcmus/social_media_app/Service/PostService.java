package com.hcmus.social_media_app.Service;

import com.hcmus.social_media_app.Dto.CreatePostRequest;
import com.hcmus.social_media_app.Dto.PostResponse;
import com.hcmus.social_media_app.Entity.Post;
import com.hcmus.social_media_app.Entity.Role;
import com.hcmus.social_media_app.Entity.User;
import com.hcmus.social_media_app.Repository.PostJpaRepository;
import com.hcmus.social_media_app.Repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    @Transactional
    public PostResponse createPost(CreatePostRequest postRequest, String userEmail) {
        User user = userJpaRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Email không tồn tại"));
        Post post = new Post(user, postRequest.getContent(), postRequest.getImageUrl(), null);
        Post savedPost = postJpaRepository.save(post);
        return PostResponse.fromEntity(savedPost);
    }

    @Transactional(readOnly = true)
    public PostResponse getPostDetail(Long id) {
        Post post = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bài viết với id: " + id));
        return PostResponse.fromEntity(post);
    }

    @Transactional
    public void deletePost(Long id, String email) {
        Post post = postJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post không tồn tại !!!"));
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User không tồn tại !!!"));

        boolean isOwner = post.getUser().getId().equals(user.getId());
        boolean isAdmin = user.getRole() == Role.ADMIN;

        if (!isOwner && !isAdmin) {
            throw new AccessDeniedException("Bạn không có quyền xoá bài viết này!");
        }

        postJpaRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPostOfAuthor(Long id, Pageable pageable) {
        return postJpaRepository.findByUserIdOrderByCreatedAtDesc(id, pageable)
                .map(PostResponse::fromEntity);
    }
}
