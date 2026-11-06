package com.hcmus.social_media_app.Controller;

import com.hcmus.social_media_app.Dto.LoginRequest;
import com.hcmus.social_media_app.Dto.RegisterRequest;
import com.hcmus.social_media_app.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> Register(@RequestBody RegisterRequest req)
    {
        try{
            authService.registerUser(req);
            return ResponseEntity.status(201).body("Đăng ký thành công !!!");
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginRequest req)
    {
        try{
            String token = authService.login(req);
            return ResponseEntity.ok(token);
        }
        catch(RuntimeException e)
        {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}
