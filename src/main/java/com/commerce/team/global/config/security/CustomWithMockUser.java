package com.commerce.team.global.config.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = CustomMockSecurityContext.class)
public @interface CustomWithMockUser {

    String name() default "나는짱";

    String email() default "midcon@nav.com";

    String password() default "asd123123";

    String role() default "ROLE_ADMIN";
}
