package hyundai.blog.question.dto;

import hyundai.blog.challenge.entity.ChallengeTil;
import hyundai.blog.member.entity.Member;
import hyundai.blog.question.entity.Question;
import hyundai.blog.question_comment.dto.QuestionCommentViewDto;

import java.util.List;

public record QuestionDetailDto(
    String challengeTilTitle,
    String profileImage,
    String questionTitle,
    String questionContent,
    Long commentCounts,
    List<QuestionCommentViewDto> questionCommentViewDtoList
) {
    public static QuestionDetailDto of(
            ChallengeTil challengeTil, Member member,
            Question question,Long commentCounts,
            List<QuestionCommentViewDto> questionCommentViewDtoList ) {
        return new QuestionDetailDto(
                challengeTil.getTitle(),
                member.getProfileImage(),
                question.getTitle(),
                question.getContent(),
                commentCounts,
                questionCommentViewDtoList
        );
    }
}
