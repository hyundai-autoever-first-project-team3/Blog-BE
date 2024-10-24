package hyundai.blog.problem_comment.dto;

public record ProblemCommentCreateRequest(
        Long problemId,
        String content
) {
}
