package hyundai.blog.question.dto;

public record QuestionCreateResponse(
        String message
) {

    public static QuestionCreateResponse of(String message) {
        return new QuestionCreateResponse(message);
    }
}
