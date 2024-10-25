package hyundai.blog.problem_comment.dto;

import hyundai.blog.problem_comment.entity.ProblemComment;

public record ProblemCommentDeleteResponse(
        Long problemCommentId,
        String message
) {

    public static ProblemCommentDeleteResponse of(ProblemComment problemComment, String message) {
        return new ProblemCommentDeleteResponse(problemComment.getId(), message);
    }

}
