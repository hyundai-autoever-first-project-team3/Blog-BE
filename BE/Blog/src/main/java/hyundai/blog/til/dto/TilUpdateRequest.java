package hyundai.blog.til.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TilUpdateRequest {
    private String language;
    private String thumbnailImage;
    private String site;
    private Long algorithmId;
    private String title;
    private String link;
    private String codeContent;
    private String content;
}
