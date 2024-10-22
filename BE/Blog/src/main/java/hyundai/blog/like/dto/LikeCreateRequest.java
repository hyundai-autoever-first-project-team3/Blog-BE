package hyundai.blog.like.dto;

import lombok.Getter;

@Getter
public class LikeCreateRequest {
    private Long memberId;
    private Long tilId;
}
