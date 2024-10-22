package hyundai.blog.question_comment.dto;

public record QuestionCommentUpdateResponse(
        String message
) {

    public static QuestionCommentUpdateResponse of(String message) {
        return new QuestionCommentUpdateResponse(message);
    }

}
