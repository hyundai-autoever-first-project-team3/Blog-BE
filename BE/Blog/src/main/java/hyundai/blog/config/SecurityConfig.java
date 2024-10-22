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

                    // 허용할 출처 설정 (모든 출처를 허용하거나 특정 도메인 추가)
                    config.setAllowedOrigins(List.of(
                            "*",  // 모든 도메인 허용
                            "http://localhost:3000",  // 로컬 개발 환경에서의 요청 허용
                            "https://codingcare.site"  // 배포된 서버의 도메인 추가
                    ));

                    // 허용할 HTTP 메서드 설정
                    config.setAllowedMethods(
                            Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

                    // 쿠키와 자격 증명 사용을 허용
                    config.setAllowCredentials(true);

                    // 허용할 헤더 설정 (모든 헤더 허용)
                    config.setAllowedHeaders(Collections.singletonList("*"));

                    // 클라이언트에서 접근 가능한 헤더 설정
                    config.setExposedHeaders(
                            Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

                    // 프리플라이트 요청의 캐싱 시간 설정 (1시간)
                    config.setMaxAge(60 * 60L);

                    return config;
                }));

        // 특정 경로는 필터 적용 제외
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/swagger-ui/index.html", "/api-docs/**", "/swagger-ui/**")
                .permitAll() // Swagger UI 및 API Docs 경로 허용
                .anyRequest().authenticated() // 나머지 경로는 인증 필요
        );

        // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
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
