package hyundai.blog.question.dto;

public record QuestionCreateRequest(
        Long challengeTilId,
        Long memberId,
        String title,
        String content

) {

}
