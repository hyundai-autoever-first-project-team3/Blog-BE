package hyundai.blog.question_comment.dto;

import hyundai.blog.member.entity.Member;
import hyundai.blog.question_comment.entity.QuestionComment;

import java.time.LocalDateTime;

public record QuestionCommentViewDto(
        String profileImage,
        String nickname,
        String commentContent,
        LocalDateTime createdAt
) {
    public static QuestionCommentViewDto of(Member member, QuestionComment questionComment) {
        return new QuestionCommentViewDto(
                member.getProfileImage(),
                member.getNickname(),
                questionComment.getContent(),
                questionComment.getCreatedAt()
        );
    }
}
