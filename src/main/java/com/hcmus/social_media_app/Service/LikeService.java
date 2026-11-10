package com.hcmus.social_media_app.Service;

import com.hcmus.social_media_app.Dto.LikeResponse;
import com.hcmus.social_media_app.Entity.Like;
import com.hcmus.social_media_app.Entity.Post;
import com.hcmus.social_media_app.Entity.User;
import com.hcmus.social_media_app.Exception.ResourceNotFoundException;
import com.hcmus.social_media_app.Repository.LikeJpaRepository;
import com.hcmus.social_media_app.Repository.PostJpaRepository;
import com.hcmus.social_media_app.Repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeJpaRepository likeJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final PostJpaRepository postJpaRepository;

    @Transactional
    public LikeResponse likePost(String email, Long postId) {
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User không tồn tại !!!"));
        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post không tồn tại !!!"));

        Optional<Like> likePost = likeJpaRepository.findByUserIdAndPostId(user.getId(), postId);
        long totalLiked = likeJpaRepository.countByPostId(postId);

        if (likePost.isPresent()) {
            likeJpaRepository.delete(likePost.get());
            LikeResponse response = new LikeResponse();
            response.setLiked(false);
            response.setTotalLiked(Math.max(0, totalLiked - 1));
            return response;
        }

        Like newLike = new Like(user, post);
        likeJpaRepository.save(newLike);

        LikeResponse response = new LikeResponse();
        response.setLiked(true);
        response.setTotalLiked(totalLiked + 1);
        return response;
    }
}
