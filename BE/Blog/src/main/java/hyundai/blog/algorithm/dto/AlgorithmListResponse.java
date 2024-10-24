package hyundai.blog.algorithm.dto;

import hyundai.blog.algorithm.entity.Algorithm;
import java.util.List;

public record AlgorithmListResponse(
        List<Algorithm> algorithmList

) {

    public static AlgorithmListResponse of(List<Algorithm> algorithmList) {
        return new AlgorithmListResponse(algorithmList);
    }

}
