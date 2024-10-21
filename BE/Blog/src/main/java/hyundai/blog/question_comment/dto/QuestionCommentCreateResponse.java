package hyundai.blog.question_comment.dto;

public record QuestionCommentCreateResponse(
        String message
) {

    public static QuestionCommentCreateResponse of(String message) {
        return new QuestionCommentCreateResponse(message);
    }

}
