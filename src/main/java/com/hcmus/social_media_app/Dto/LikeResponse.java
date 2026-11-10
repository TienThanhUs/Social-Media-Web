package com.hcmus.social_media_app.Dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponse {
    private boolean liked;
    private long totalLiked;
}
