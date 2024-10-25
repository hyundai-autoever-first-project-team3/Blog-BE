package hyundai.blog.til.repository;

import hyundai.blog.til.entity.Til;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TilRepository extends JpaRepository<Til, Long> {

    Page<Til> findAll(Pageable pageable);

    Page<Til> findAllByMemberId(Long memberId, Pageable pageable);

    Long countByMemberId(Long id);

    List<Til> findAllByMemberId(Long memberId);

    // 특정 사용자(memberId)와 특정 달에 작성된 글 개수 조회
    @Query("SELECT COUNT(t) FROM Til t WHERE t.memberId = :memberId " +
            "AND MONTH(t.createdAt) = MONTH(CURRENT_DATE) AND YEAR(t.createdAt) = YEAR(CURRENT_DATE)")
    long countByMemberIdAndCreatedAtThisMonth(@Param("memberId") Long memberId);
}
