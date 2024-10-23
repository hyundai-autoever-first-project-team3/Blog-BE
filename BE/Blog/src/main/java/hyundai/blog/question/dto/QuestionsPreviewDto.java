package hyundai.blog.question.dto;

import hyundai.blog.challenge.entity.ChallengeTil;
import hyundai.blog.member.entity.Member;
import hyundai.blog.question.entity.Question;
import hyundai.blog.question_comment.entity.QuestionComment;

import java.time.LocalDateTime;

public record QuestionsPreviewDto(
        String siteKinds,
        String challengeTilTitle,
        String questionTitle,
        String nickname,
        LocalDateTime createdAt,
        Long commentsCount,
        String profileImage
) {
    public static QuestionsPreviewDto of(
            Member member, ChallengeTil challengeTil,
            Question question, Long commentsCount) {
        return new QuestionsPreviewDto(
                challengeTil.getSiteKinds(),
                challengeTil.getTitle(),
                question.getTitle(),
                member.getNickname(),
                question.getCreatedAt(),
                commentsCount,
                member.getProfileImage()
        );
    }
}
