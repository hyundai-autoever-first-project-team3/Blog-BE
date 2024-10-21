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
    private Long memberId;
    private String language;
    private String site;
    private String algorithm;
    private String title;
    private String tag;
    private String link;
    private String codeContent;
    private String content;
}
