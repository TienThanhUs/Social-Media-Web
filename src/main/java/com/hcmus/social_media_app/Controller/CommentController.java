package com.hcmus.social_media_app.Controller;

import com.hcmus.social_media_app.Dto.PostCommentRequest;
import com.hcmus.social_media_app.Dto.PostCommentResponse;
import com.hcmus.social_media_app.Service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/{postId}/comments")
    public ResponseEntity<Page<PostCommentResponse>> getAllComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        int safePage = Math.max(0, page);
        int safeSize = Math.min(Math.max(1, size), 100);
        Pageable pageable = PageRequest.of(safePage, safeSize, Sort.by("createdAt").descending());
        Page<PostCommentResponse> comments = commentService.getAllCommentFromPost(postId, pageable);
        return ResponseEntity.ok(comments);
    }
}
