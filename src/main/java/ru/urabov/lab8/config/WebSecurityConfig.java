package ru.urabov.lab8.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    // Пароли хранятся в виде хеша BCrypt
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Правила проверяются сверху вниз, срабатывает первое подходящее
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index", "/register/**", "/login", "/error").permitAll()
                        .requestMatchers("/users", "/deleteStudent").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/list", true)
                        .failureUrl("/login?error")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                // Пользователь без нужной роли попадает на свою страницу, а не на стандартную ошибку 403
                .exceptionHandling(ex -> ex.accessDeniedPage("/access-denied"));
        // Защита от CSRF оставлена включённой: Thymeleaf сам добавляет токен в формы с th:action
        return http.build();
    }
}
