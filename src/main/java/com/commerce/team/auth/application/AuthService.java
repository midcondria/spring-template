package com.commerce.team.auth.application;

import com.commerce.team.auth.domain.PasswordEncoder;
import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void checkEmailDuplicate(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }

    @Transactional
    public void signup(NormalSignupRequest request) {
        checkEmailDuplicate(request.getEmail());

        String encryptedPassword = passwordEncoder.encrypt(request.getPassword());
        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(encryptedPassword)
            .build();

        userRepository.save(user);
    }
}
