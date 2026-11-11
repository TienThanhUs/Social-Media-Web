package com.hcmus.social_media_app.Dto;

import com.hcmus.social_media_app.Entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostCommentResponse {
    private Long id;
    private Long postId;
    private Long userId;
    private String authorUsername;
    private String content;
    private LocalDateTime createdAt;

    public static PostCommentResponse fromEntity(Comment comment) {
        return PostCommentResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .authorUsername(comment.getUser().getUsername())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
