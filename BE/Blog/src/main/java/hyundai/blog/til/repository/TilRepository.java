package hyundai.blog.til.repository;

import hyundai.blog.til.entity.Til;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TilRepository extends JpaRepository<Til, Long> {

    Page<Til> findAll(Pageable pageable);

    Page<Til> findAllByMemberId(Long memberId, Pageable pageable);
}
