package hyundai.blog.til.entity;

import hyundai.blog.til.dto.TilUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TIL")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Til {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String language;
    private String site;
    private String algorithm;
    private String title;
    private String tag;
    private String link;

    @Column(name = "code_content")
    private String codeContent;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void change(TilUpdateRequest request){
        this.language = request.getLanguage();
        this.site = request.getSite();
        this.algorithm = request.getAlgorithm();
        this.title = request.getTitle();
        this.tag = request.getTag();
        this.link = request.getLink();
        this.codeContent = request.getCodeContent();
        this.content = request.getContent();
        this.updatedAt = LocalDateTime.now();
    }
}
