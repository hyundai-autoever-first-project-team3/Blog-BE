package hyundai.blog.til.entity;

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
}
