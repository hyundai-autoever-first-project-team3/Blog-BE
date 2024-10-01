package hyundai.blog.config.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import hyundai.blog.config.jwt.JwtTokenProvider;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 사용자 정보 가져오기 (예: 카카오 프로필 정보)
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

        String nickname = (String) properties.get("nickname");
        String profileImage = (String) properties.get("profile_image");
        String email = (String) kakaoAccount.get("email");

        // JWT AccessToken 및 RefreshToken 생성
        String accessToken = jwtTokenProvider.createAccessToken(nickname, profileImage, email,
                "ROLE_USER");
        String refreshToken = jwtTokenProvider.createRefreshToken(email);

        // RefreshToken을 HttpOnly 쿠키에 저장
        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7일

        response.addCookie(refreshTokenCookie);

        // AccessToken은 클라이언트가 사용할 수 있도록 응답 헤더에 추가
        response.setHeader("Authorization", "Bearer " + accessToken);

        System.out.println(nickname + " / " + profileImage + " / " + email);

        // 로그인 성공 후 원하는 페이지로 리다이렉트
        response.sendRedirect("http://127.0.0.1:5500/index.html");
    }
}
