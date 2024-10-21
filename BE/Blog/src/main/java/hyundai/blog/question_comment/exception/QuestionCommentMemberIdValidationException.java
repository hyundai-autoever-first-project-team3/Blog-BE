package hyundai.blog.question_comment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class QuestionCommentMemberIdValidationException extends RuntimeException {

    public QuestionCommentMemberIdValidationException() {
        super("[ERROR] 작성자와 다른 사용자는 접근할 수 없습니다.");
    }
}
