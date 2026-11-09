package com.hcmus.social_media_app.Controller;

import com.hcmus.social_media_app.Dto.CreatePostRequest;
import com.hcmus.social_media_app.Dto.PostResponse;
import com.hcmus.social_media_app.Service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody CreatePostRequest req, Authentication authentication) {
        String email = authentication.getName();
        PostResponse response = postService.createPost(req, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostResponse response = postService.getPostDetail(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String email = authentication.getName();
        postService.deletePost(id, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<Page<PostResponse>> getAllPostOfAuthor(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        size = Math.min(size, 100);
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(postService.getAllPostOfAuthor(id, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> revisePost(@PathVariable Long id, @Valid @RequestBody CreatePostRequest postRequest, Authentication authentication)
    {
        String email = authentication.getName();
        PostResponse response = postService.updatePost(id, postRequest, email);
        return ResponseEntity.ok(response);
    }
}
