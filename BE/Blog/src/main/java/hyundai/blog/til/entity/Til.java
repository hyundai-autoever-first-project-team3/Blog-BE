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

    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "thumbnail_image")
    private String thumbnailImage;
    private String language;
    private String site;
    @Column(name = "algorithm_id")
    private Long algorithmId;
    private String title;
    private String link;

    @Column(name = "code_content")
    private String codeContent;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void change(TilUpdateRequest request){
        this.language = request.getLanguage();
        this.thumbnailImage = request.getThumbnailImage();
        this.site = request.getSite();
        this.algorithmId = request.getAlgorithmId();
        this.title = request.getTitle();
        this.link = request.getLink();
        this.codeContent = request.getCodeContent();
        this.content = request.getContent();
        this.updatedAt = LocalDateTime.now();
    }
}
