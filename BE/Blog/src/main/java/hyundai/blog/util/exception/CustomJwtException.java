package hyundai.blog.util.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)

public class CustomJwtException extends BusinessException {
    public CustomJwtException(String msg) {
        super(msg);
    }
}
