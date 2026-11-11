package com.hcmus.social_media_app.Controller;

import com.hcmus.social_media_app.Dto.PostCommentRequest;
import com.hcmus.social_media_app.Dto.PostCommentResponse;
import com.hcmus.social_media_app.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostCommentResponse> postComment(
            @PathVariable Long postId,
            Authentication authentication,
            @Valid @RequestBody PostCommentRequest req
    ) {
        String email = authentication.getName();
        PostCommentResponse response = commentService.postComment(postId, email, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
