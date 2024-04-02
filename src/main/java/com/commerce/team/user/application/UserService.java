package com.commerce.team.user.application;

import com.commerce.team.global.exception.UserAlreadyExistsException;
import com.commerce.team.user.domain.User;
import com.commerce.team.user.dto.UserSignupRequest;
import com.commerce.team.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public void signup(UserSignupRequest request) {
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
