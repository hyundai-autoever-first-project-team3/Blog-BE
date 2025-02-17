package hyundai.blog.comment.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CommentIdNotFoundException extends BusinessException {
    public CommentIdNotFoundException() {
        super("[ERROR] 해당 comment Id는 존재하지 않습니다.");
    }
}
