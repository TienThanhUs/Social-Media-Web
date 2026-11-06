package com.hcmus.social_media_app.Service;

import com.hcmus.social_media_app.Dto.LoginRequest;
import com.hcmus.social_media_app.Dto.RegisterRequest;
import com.hcmus.social_media_app.Entity.Role;
import com.hcmus.social_media_app.Entity.User;
import com.hcmus.social_media_app.Repository.UserJpaRepository;
import com.hcmus.social_media_app.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserJpaRepository userJpaRepository;
    private final JwtService jwtService;
    public void registerUser(RegisterRequest registerRequest)
    {
        String email = registerRequest.getEmail();
        String username = registerRequest.getUsername();
        String password = registerRequest.getPassword();
        if(userJpaRepository.existsByEmail(email))
        {
            throw new RuntimeException("Email đã tồn tại !!!");
        }
        if(userJpaRepository.existsByUsername(username))
        {
            throw new RuntimeException("Username đã tồn tại !!!");
        }
        User newUser = new User(username,email,passwordEncoder.encode(password), LocalDateTime.now(), Role.USER);
        userJpaRepository.save(newUser);
    }
    public String login(LoginRequest loginRequest)
    {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        User user = userJpaRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("Email hoặc mật khẩu không tồn tại !!!"));
        if(!passwordEncoder.matches(password,user.getPasswordHash()))
        {
            throw new RuntimeException("Email hoặc mật khẩu không đúng !!!");
        }
        String token = jwtService.generateToken(user);
        return token;
    }
}
