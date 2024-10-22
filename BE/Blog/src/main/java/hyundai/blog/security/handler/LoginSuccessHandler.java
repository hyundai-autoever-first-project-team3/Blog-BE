package hyundai.blog.security.handler;

import hyundai.blog.security.entity.OAuth2UserInfo;
import hyundai.blog.util.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // 1. OAuth2User 객체 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 2. OAuth2UserInfo 객체 생성
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        OAuth2UserInfo userInfo = OAuth2UserInfo.of(registrationId, oAuth2User.getAttributes());

        // 3. JWT 토큰 생성
        Map<String, Object> claims = Map.of(
                "email", userInfo.email(),
                "name", userInfo.name(),
                "profileImage", userInfo.profileImage(),
                "social", userInfo.social(),
                "role", userInfo.role()
        );

        String accessToken = jwtTokenProvider.generateAccessToken(claims);
        String refreshToken = jwtTokenProvider.createRefreshToken();

        // 4. 헤더에 JWT 토큰 설정
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Refresh-Token", refreshToken);

//        // 5. 사용자 정보 응답 바디에 포함 (선택 사항)
//        response.setContentType("application/json");
//        response.getWriter().write(String.format("{\"accessToken\": \"%s\", \"refreshToken\": \"%s\", \"email\": \"%s\", \"name\": \"%s\", \"profileImage\": \"%s\", \"social\": \"%s\", \"role\": \"%s\"}",
//                accessToken, refreshToken, userInfo.email(), userInfo.name(), userInfo.profileImage(), userInfo.social(), userInfo.role()));

        // 5. 사용자 정보 및 토큰을 포함한 리다이렉트
        String redirectUrl = "/";

        response.sendRedirect(redirectUrl);
    }
}
