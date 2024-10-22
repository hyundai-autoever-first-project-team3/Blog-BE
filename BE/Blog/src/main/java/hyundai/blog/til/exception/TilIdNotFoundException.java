package hyundai.blog.til.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TilIdNotFoundException extends RuntimeException{
    public TilIdNotFoundException() {
        super("[ERROR] 해당 til Id는 존재하지 않습니다.");
    }
}
