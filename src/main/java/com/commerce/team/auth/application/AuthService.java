package com.commerce.team.auth.application;

import com.commerce.team.global.config.AppConfig;
import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional(readOnly = true)
public class AuthService {

    private final AppConfig appConfig;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void checkEmailDuplicate(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    @Transactional
    public String signup(NormalSignupRequest request) {
        checkEmailDuplicate(request.getEmail());

        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(encryptedPassword)
            .build();

        userRepository.save(user);
        return generateAccessToken(request.getName(), new Date());
    }

    private String generateAccessToken(String name, Date dateTime) {
        SecretKey secretKey = appConfig.getSecretKey();
        return Jwts.builder()
            .subject(name)
            .issuedAt(dateTime)
            .expiration(new Date(dateTime.getTime() + 36000L))
            .signWith(secretKey)
            .compact();
    }

    public void login() {
        log.info("로그인 로직 호출");
    }
}
