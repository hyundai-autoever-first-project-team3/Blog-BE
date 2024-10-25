package hyundai.blog.like.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LikeDuplicateException extends BusinessException {

    public LikeDuplicateException() {
        super("[ERROR] 중복된 좋아요는 할 수 없습니다.");
    }
}
