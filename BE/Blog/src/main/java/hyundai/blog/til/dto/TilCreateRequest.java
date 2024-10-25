package hyundai.blog.til.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TilCreateRequest {
    private String language;
    private String thumbnailImage;
    private String site;
    private Long algorithmId;
    private String title;
    private String link;
    private String codeContent;
    private String content;
}
