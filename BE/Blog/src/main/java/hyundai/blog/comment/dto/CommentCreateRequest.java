package hyundai.blog.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentCreateRequest {
    private Long userId;
    private Long tilId;
    private String content;
}
