package hyundai.blog.example;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationTestController {


    @GetMapping("/example")
    public String getAuthenticatedUser() {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 인증된 사용자의 이름 반환 (예: 사용자 이름 또는 이메일)
        return "Authenticated user: " + authentication.getName();
    }
}
