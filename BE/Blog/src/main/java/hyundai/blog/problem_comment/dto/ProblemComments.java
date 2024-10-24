package hyundai.blog.problem_comment.dto;

import hyundai.blog.member.entity.Member;
import hyundai.blog.problem.entity.Problem;
import hyundai.blog.problem_comment.entity.ProblemComment;
import java.time.LocalDateTime;

public record ProblemComments (
        String nickname,
        String profileImage,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static ProblemComments of(
            Member member, ProblemComment problemComment
    ) {
        return new ProblemComments(
                member.getNickname(),
                member.getProfileImage(),
                problemComment.getContent(),
                problemComment.getCreatedAt(),
                problemComment.getUpdatedAt()
        );
    }
}
