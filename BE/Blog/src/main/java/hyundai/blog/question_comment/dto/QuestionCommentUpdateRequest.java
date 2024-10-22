package hyundai.blog.question_comment.dto;

public record QuestionCommentUpdateRequest(
        Long memberId,
        String content
) {

}
