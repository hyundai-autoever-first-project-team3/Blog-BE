package hyundai.blog.til.repository;

import hyundai.blog.til.entity.Til;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TilRepository extends JpaRepository<Til, Long> {
}
