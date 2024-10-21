package hyundai.blog.algorithm.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "ALGORITHM")
@Entity
@RequiredArgsConstructor
@Getter
public class Algorithm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "kor_classification")
    private String korClassification;
    @Column(name = "eng_classification")
    private String engClassification;
    @Column(name = "used_at")
    private LocalDateTime usedAt;

    public void updateUsedAt() {
        this.usedAt = LocalDateTime.now();
    }
}
