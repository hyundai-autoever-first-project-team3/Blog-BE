package hyundai.blog.challenge.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChallengeIdNotFoundException extends BusinessException {
    public ChallengeIdNotFoundException() {
        super("[ERROR] 해당 ChallengeId는 존재하지 않습니다.");
    }
}
