package hyundai.blog.util;

import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberEmailNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberResolver {
    private final MemberRepository memberRepository;

    public Member getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();  // authentication에서 email 추출
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberEmailNotFoundException::new);
    }
}
