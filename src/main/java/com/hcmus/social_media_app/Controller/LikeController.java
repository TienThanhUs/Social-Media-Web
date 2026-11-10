package com.hcmus.social_media_app.Controller;

import com.hcmus.social_media_app.Dto.LikeResponse;
import com.hcmus.social_media_app.Service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService service;

    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeResponse> likeAPost(@PathVariable long postId, Authentication authentication) {
        String email = authentication.getName();
        LikeResponse response = service.likePost(email, postId);
        if (response.isLiked()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
