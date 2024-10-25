package hyundai.blog.challenge.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "CHALLENGE")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String algorithm;
    private Long views;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 조회수 증가 메서드
    public void incrementViews() {
        this.views += 1;
    }
}
