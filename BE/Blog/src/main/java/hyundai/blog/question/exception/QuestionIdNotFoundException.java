package hyundai.blog.question.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class QuestionIdNotFoundException extends BusinessException {

    public QuestionIdNotFoundException() {
        super("[ERROR] 해당 questionId는 존재하지 않습니다.");
    }
}

