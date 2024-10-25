package hyundai.blog.problem.repository;

import hyundai.blog.problem.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    List<Problem> findAllByChallengeId(Long challengeId);

    Problem findByChallengeId(Long challengeId);
}
