package com.commerce.team.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class NormalSignupRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Builder
    public NormalSignupRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
