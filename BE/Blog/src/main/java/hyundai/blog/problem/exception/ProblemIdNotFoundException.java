package hyundai.blog.problem.exception;

import hyundai.blog.exception.BusinessException;

public class ProblemIdNotFoundException extends BusinessException {

    public ProblemIdNotFoundException() {
        super("[ERROR] 해당하는 problem id는 존재하지 않습니다.");
    }
}
