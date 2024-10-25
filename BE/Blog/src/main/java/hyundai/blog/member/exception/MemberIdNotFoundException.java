package hyundai.blog.member.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberIdNotFoundException extends BusinessException {
    public MemberIdNotFoundException() {
        super("[ERROR] 해당 memberId는 존재하지 않습니다.");
    }
}
