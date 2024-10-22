package hyundai.blog.config;

import hyundai.blog.security.filter.JwtAuthenticationFilter;
import hyundai.blog.security.handler.LoginSuccessHandler;
import hyundai.blog.security.service.CustomUserDetailService;
import hyundai.blog.util.JwtTokenProvider;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailService oAuth2UserService;
    private final LoginSuccessHandler oAuth2SuccessHandler;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {

        // 쿠키를 사용하지 않으므로 CSRF Disable
        http.csrf(AbstractHttpConfigurer::disable);

        // Session 사용 해제
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS));

        // cors 설정
        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(
                request -> {
                    CorsConfiguration config = new CorsConfiguration();

                    config.setAllowedOrigins(List.of(
                            "*",
                            "http://localhost:5173"
                    ));
                    config.setAllowedMethods(
                            Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    config.setAllowCredentials(true);
                    config.setAllowedHeaders(Collections.singletonList("*"));
                    config.setExposedHeaders(
                            Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
                    config.setMaxAge(60 * 60L);

                    return config;
                }));


//        // 특정 경로는 필터 적용 제외
//        http.authorizeHttpRequests(authz -> authz
//                .requestMatchers("/swagger-ui/index.html", "/api-docs/**", "/swagger-ui/**")
//                .permitAll() // Swagger UI 및 API Docs 경로 허용
//                .anyRequest().authenticated() // 나머지 경로는 인증 필요
//        );
//
//        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
//        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                UsernamePasswordAuthenticationFilter.class);

        // OAuth2 로그인 설정
        http.oauth2Login(oauth ->
                oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        .successHandler(oAuth2SuccessHandler)
        );

        return http.build();
    }
}
