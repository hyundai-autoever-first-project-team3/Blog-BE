package hyundai.blog.problem_comment.dto;

import hyundai.blog.problem_comment.entity.ProblemComment;

public record ProblemCommentCreateResponse(
        Long problemCommentId,
        String message
) {

    public static ProblemCommentCreateResponse of(ProblemComment problemComment, String message) {
        return new ProblemCommentCreateResponse(problemComment.getId(), message);
    }
}
