package hyundai.blog.algorithm.repository;

import hyundai.blog.algorithm.entity.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
    Algorithm findFirstByOrderByUsedAtAsc();
}
