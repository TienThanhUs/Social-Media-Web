package com.hcmus.social_media_app.Dto.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorException
{
    private int status;
    private String message;
    private String description;
    private LocalDateTime timestamp;

    public ErrorException(int status, String message, String description) {
        this.status = status;
        this.message = message;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }
}
