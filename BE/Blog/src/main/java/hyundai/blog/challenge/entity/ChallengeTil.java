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
@Table(name = "CHALLENGE_TIL")
public class ChallengeTil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "challenge_id")
    private Long challengeId;
    private String title;
    private String site;

    @Column(name = "site_kinds")
    private String siteKinds;
    private String level;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
