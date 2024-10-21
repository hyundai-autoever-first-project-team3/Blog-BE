package hyundai.blog.question.dto;

public record QuestionDeleteResponse(
        String message
) {

    public static QuestionDeleteResponse of(String message) {
        return new QuestionDeleteResponse(message);
    }
}
