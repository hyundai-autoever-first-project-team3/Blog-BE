package hyundai.blog.question_comment.dto;

public record QuestionCommentCreateRequest(
        Long memberId,
        String content
) {

}
