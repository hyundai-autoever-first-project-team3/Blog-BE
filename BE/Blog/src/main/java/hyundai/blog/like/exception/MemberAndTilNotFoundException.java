package hyundai.blog.like.exception;

import hyundai.blog.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MemberAndTilNotFoundException extends BusinessException {
    public MemberAndTilNotFoundException() {
        super("[ERROR] 해당 MemberId와 TilId는 존재하지 않습니다.");
    }
}
