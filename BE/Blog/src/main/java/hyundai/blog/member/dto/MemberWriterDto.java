package hyundai.blog.member.dto;

import hyundai.blog.member.entity.Member;

public record MemberWriterDto(
        Long id,
        String nickname,
        String profileImage,
        String intro
) {

    public static MemberWriterDto of(Member member) {
        return new MemberWriterDto(
                member.getId(),
                member.getNickname(),
                member.getProfileImage(),
                member.getIntro()
        );
    }

}
