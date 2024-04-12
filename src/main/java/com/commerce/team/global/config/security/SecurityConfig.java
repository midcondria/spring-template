package com.commerce.team.global.config.security;

import com.commerce.team.global.config.security.filter.EmailPasswordAuthFilter;
import com.commerce.team.global.config.security.handler.Http401Handler;
import com.commerce.team.global.config.security.handler.Http403Handler;
import com.commerce.team.global.config.security.handler.LoginFailHandler;
import com.commerce.team.user.domain.User;
import com.commerce.team.user.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity(debug = true) // TODO: 운영 환경에선 제거
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers("/favicon.ico")
            .requestMatchers("/error")
            .requestMatchers(toH2Console()); // toH2Console() = new AntPathRequestMatcher("/h2-console/**")
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/auth/login").permitAll()
                .requestMatchers("/auth/signup").permitAll()
                .requestMatchers("/users").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/users/admin").hasRole("ADMIN")
//                .access(new WebExpressionAuthorizationManager("hasRole('ADMIN') AND hasAuthority('WRITE')"))
                .anyRequest().authenticated()
            )
//            .formLogin(form -> form
//                .loginPage("/auth/login")
//                .loginProcessingUrl("/auth/login")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .defaultSuccessUrl("/auth")
//                .failureHandler(new LoginFailHandler(objectMapper))
//            )
            .addFilterBefore(usernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(e -> {
                e.authenticationEntryPoint(new Http401Handler(objectMapper)); // 로그인 안했을 때
                e.accessDeniedHandler(new Http403Handler(objectMapper));  // 로그인은 했는데 권한이 없을 때
            })
//            .rememberMe(rm -> rm.rememberMeParameter("remember")
//                .alwaysRemember(false)
//                .tokenValiditySeconds(2592000)
//            )
            .csrf(AbstractHttpConfigurer::disable)
            .build();
    }

    @Bean
    public EmailPasswordAuthFilter usernamePasswordAuthenticationFilter() {
        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter("/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/auth"));
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        rememberMeServices.setValiditySeconds(3600 * 24 * 30);
//        filter.setRememberMeServices(rememberMeServices);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(userRepository));
        provider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
            return new UserPrincipal(user);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64
        );
    }
}
