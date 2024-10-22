package hyundai.blog.question.dto;

public record QuestionUpdateResponse(
        String message
) {

    public static QuestionUpdateResponse of(String message) {
        return new QuestionUpdateResponse(message);
    }

}
