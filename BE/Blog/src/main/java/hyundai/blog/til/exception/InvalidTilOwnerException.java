package hyundai.blog.til.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidTilOwnerException extends RuntimeException {
    public InvalidTilOwnerException() {
        super("[ERROR] TIL 작성자와 로그인한 사용자가 일치하지 않습니다.");
    }
}