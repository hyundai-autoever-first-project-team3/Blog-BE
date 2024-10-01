package hyundai.blog.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        // "/admin/**" 경로에 대해서만 인증 필요 [추후 변경]
                        .requestMatchers("/admin/**").authenticated()
                        // 그 외의 경로는 인증 없이 접근 가능
                        .anyRequest().permitAll()
                )
                .oauth2Login((oauth2) -> oauth2
                        .successHandler(successHandler) // 성공 핸들러 등록
                );
        return http.build();
    }
}
