package hyundai.blog.til.dto;

import hyundai.blog.algorithm.entity.Algorithm;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import java.time.LocalDateTime;

public record TilPreviewDto(
        Long tilId,
        String thumbnailImage,
        String title,
        String content,
        String algorithm,
        LocalDateTime createdAt,
        Long commentCount,
        Long likeCount,
        String writerNickname,
        String writerProfileImage
) {

    public static TilPreviewDto of(Til til, Member member, Algorithm algorithm, Long commentCount,
            Long likeCount) {
        return new TilPreviewDto(
                til.getId(),
                til.getThumbnailImage(),
                til.getTitle(),
                til.getContent(),
                algorithm.getEngClassification(),
                til.getCreatedAt(),
                commentCount,
                likeCount,
                member.getNickname(),
                member.getProfileImage()
        );
    }

}
