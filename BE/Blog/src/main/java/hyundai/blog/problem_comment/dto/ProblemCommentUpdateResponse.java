package hyundai.blog.problem_comment.dto;

import hyundai.blog.problem_comment.entity.ProblemComment;

public record ProblemCommentUpdateResponse(
        Long problemCommentId,
        String message
) {

    public static ProblemCommentUpdateResponse of(ProblemComment problemComment, String message) {
        return new ProblemCommentUpdateResponse(problemComment.getId(), message);
    }
}
