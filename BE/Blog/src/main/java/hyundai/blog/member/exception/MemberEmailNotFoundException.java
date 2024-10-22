package hyundai.blog.member.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberEmailNotFoundException extends RuntimeException{
    public MemberEmailNotFoundException() {
        super("[ERROR] 해당 member Email은 존재하지 않습니다.");
    }
}
