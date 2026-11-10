package com.hcmus.social_media_app.Exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message)
    {
        super(message);
    }
}
