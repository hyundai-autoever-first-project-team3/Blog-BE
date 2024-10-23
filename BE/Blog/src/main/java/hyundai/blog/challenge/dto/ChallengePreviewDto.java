package hyundai.blog.challenge.dto;

import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.dto.TilPreviewDto;
import hyundai.blog.til.entity.Til;

import java.time.LocalDateTime;

public record ChallengePreviewDto(
        String algorithm,
        String title,
        Long views,
        LocalDateTime createdAt
) {
    public static ChallengePreviewDto of(Challenge challenge) {
        return new ChallengePreviewDto(
                challenge.getAlgorithm(),
                challenge.getTitle(),
                challenge.getViews(),
                challenge.getCreatedAt()
        );
    }

}
