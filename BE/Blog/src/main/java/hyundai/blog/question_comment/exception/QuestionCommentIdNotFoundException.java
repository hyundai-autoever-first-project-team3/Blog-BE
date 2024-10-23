package hyundai.blog.question_comment.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class QuestionCommentIdNotFoundException extends BusinessException {

    public QuestionCommentIdNotFoundException() {
        super("[ERROR] 해당 questionCommentId는 존재하지 않습니다.");
    }
}
