package hyundai.blog.challenge.repository;

import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.challenge.entity.ChallengeTil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeTilRepository extends JpaRepository<ChallengeTil, Long> {

    List<ChallengeTil> findAllByChallengeId(Long challengeId);
}
