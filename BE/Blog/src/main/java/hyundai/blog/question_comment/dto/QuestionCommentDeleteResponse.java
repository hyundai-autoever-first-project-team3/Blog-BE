package hyundai.blog.question_comment.dto;

import hyundai.blog.question.dto.QuestionDeleteResponse;

public record QuestionCommentDeleteResponse(
        String message
) {

    public static QuestionCommentDeleteResponse of(String message) {
        return new QuestionCommentDeleteResponse(message);
    }

}
