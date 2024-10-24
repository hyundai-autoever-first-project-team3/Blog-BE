package hyundai.blog.member.service;

import hyundai.blog.member.dto.MemberUpdateRequest;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.util.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberResolver memberResolver;

    public Member getMemberInfo() {
        Member member = memberResolver.getCurrentMember();

        return member;
    }

    @Transactional
    public Member updateMemberInfo(MemberUpdateRequest request) {
        Member member = memberResolver.getCurrentMember();

        member.updateNicknameAndIntro(request);

        return member;
    }
}
