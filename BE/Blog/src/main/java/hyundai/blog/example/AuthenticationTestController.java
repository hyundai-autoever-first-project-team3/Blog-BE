package hyundai.blog.example;

import hyundai.blog.member.entity.Member;
import hyundai.blog.util.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationTestController {

    private final MemberResolver memberResolver;

    @GetMapping("/example")
    public String getAuthenticatedUser() {
        // 현재 인증된 사용자 정보 가져오기
        Member member = memberResolver.getCurrentMember();

        // 인증된 사용자의 이름 반환 (예: 사용자 이름 또는 이메일)
        return "Authenticated user: " + member.getEmail();
    }
}
