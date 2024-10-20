package hyundai.blog.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        // 1. OAuth2User 객체 가져오기
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 2. 사용자 속성 정보 출력
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("OAuth2 User Attributes: " + attributes);

        // 3. 사용자 권한 정보 출력
        for (GrantedAuthority authority : oAuth2User.getAuthorities()) {
            System.out.println("Granted Authority: " + authority.getAuthority());
        }

        // 4. 로그인 성공 후 리다이렉트
        response.sendRedirect("/");  // 원하는 URL로 리다이렉트 가능
    }
}
