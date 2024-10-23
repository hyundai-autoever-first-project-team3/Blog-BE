package hyundai.blog.til.dto;

import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import java.time.LocalDateTime;

public record TilPreviewDto(
        String thumbnailImage,
        String title,
        String content,
        LocalDateTime createdAt,
        Long commentCount,
        Long likeCount,
        String writerNickname,
        String writerProfileImage
) {

    public static TilPreviewDto of(Til til, Member member, Long commentCount, Long likeCount) {
        return new TilPreviewDto(
                til.getThumbnailImage(),
                til.getTitle(),
                til.getContent(),
                til.getCreatedAt(),
                commentCount,
                likeCount,
                member.getNickname(),
                member.getProfileImage()
        );
    }

}
