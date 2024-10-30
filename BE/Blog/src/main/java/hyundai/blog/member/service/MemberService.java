package hyundai.blog.member.service;

import hyundai.blog.member.dto.MemberProfileUpdateRequest;
import hyundai.blog.member.dto.MemberUpdateRequest;
import hyundai.blog.member.entity.Member;
import hyundai.blog.util.MemberResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

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

    @Transactional
    public Member updateMemberProfile(MemberProfileUpdateRequest request) {
        Member member = memberResolver.getCurrentMember();

        member.updateProfileImage(request.profileImage());

        return member;
    }
}
