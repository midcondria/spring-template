package com.commerce.team.auth.application;

import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.auth.dto.NormalSignupRequest;
import com.commerce.team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;

    public void signup(NormalSignupRequest request) {
        checkEmailDuplicate(request.getEmail());

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(request.getPassword())
            .build();

        userRepository.save(user);
    }

    private void checkEmailDuplicate(String email) {
        Optional<User> foundUser = userRepository.findByEmail(email);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
    }
}
