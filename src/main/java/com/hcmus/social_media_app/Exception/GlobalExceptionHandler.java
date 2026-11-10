package com.hcmus.social_media_app.Exception;

import com.hcmus.social_media_app.Dto.exception.ErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorException> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorException errorResponse = new ErrorException(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                "Tài nguyên yêu cầu không tìm thấy"
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorException> handleUnauthorizedException(UnauthorizedException ex) {
        ErrorException errorException = new ErrorException(
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                "Tài khoản chưa xác thực !!!"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorException);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorException> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorException errorException = new ErrorException(
                HttpStatus.FORBIDDEN.value(),
                ex.getMessage(),
                "Bạn không có quyền thực hiện hành động này"
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorException);
    }
}
