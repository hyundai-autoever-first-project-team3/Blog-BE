package hyundai.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 기본 상태 코드 설정
public class BusinessException extends RuntimeException {
    public BusinessException(String msg) {
        super(msg);
    }
}
