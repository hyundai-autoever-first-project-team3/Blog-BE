package hyundai.blog.til.dto;

import lombok.*;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class TilAlgorithmDto {

    private final int totalCount;
    private final Map<String, Integer> algorithmCount;
}





