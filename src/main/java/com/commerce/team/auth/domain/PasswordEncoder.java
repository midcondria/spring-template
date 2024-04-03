package com.commerce.team.auth.domain;

public interface PasswordEncoder {

    String encrypt(String rawPassword);

    boolean matches(String rawPassword, String encryptedPassword);
}
