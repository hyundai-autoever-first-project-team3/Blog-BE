package hyundai.blog.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberIdNotFoundException extends RuntimeException {
    public MemberIdNotFoundException() {
        super("[ERROR] 해당 memberId는 존재하지 않습니다.");
    }
}
