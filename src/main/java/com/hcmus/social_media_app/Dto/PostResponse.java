package com.hcmus.social_media_app.Dto;

import com.hcmus.social_media_app.Entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long id;
    private Long userId;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    public static PostResponse fromEntity(Post post)
    {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(post.getId());
        postResponse.setContent(post.getContent());
        postResponse.setImageUrl(post.getImageUrl());
        postResponse.setCreatedAt(post.getCreatedAt());
        postResponse.setUserId(post.getUser().getId());
        return postResponse;
    }
}
