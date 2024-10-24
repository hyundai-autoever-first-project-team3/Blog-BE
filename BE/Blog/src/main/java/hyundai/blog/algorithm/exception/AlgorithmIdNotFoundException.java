package hyundai.blog.algorithm.exception;

import hyundai.blog.exception.BusinessException;

public class AlgorithmIdNotFoundException extends BusinessException {

    public AlgorithmIdNotFoundException() {
        super("[ERROR] 해당 알고리즘 아이디는 존재하지 않습니다.");
    }
}
