package hyundai.blog.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LikeCreateResponse {
//    private boolean isLiked;
    private Long likeCounts;
}
