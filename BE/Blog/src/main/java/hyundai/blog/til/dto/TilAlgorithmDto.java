package hyundai.blog.til.dto;

import hyundai.blog.algorithm.dto.AlgorithmCountDto;
import java.util.List;
import lombok.*;

import java.util.Map;

@Getter
@RequiredArgsConstructor
@ToString
public class TilAlgorithmDto {

    private final int totalCount;
    private final List<AlgorithmCountDto> algorithmCountList;
}





