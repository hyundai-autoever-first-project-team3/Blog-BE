package hyundai.blog.challenge.repository;

import hyundai.blog.challenge.entity.Challenge;
import hyundai.blog.til.entity.Til;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    Page<Challenge> findAll(Pageable pageable);
}
