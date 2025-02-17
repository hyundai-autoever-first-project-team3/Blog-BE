package hyundai.blog.challenge.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class ChallengeTilIdNotFoundException extends BusinessException {
    public ChallengeTilIdNotFoundException() {
        super("[ERROR] 해당 challengeTilId는 존재하지 않습니다.");
    }
}
