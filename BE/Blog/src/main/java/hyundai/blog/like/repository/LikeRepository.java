package hyundai.blog.like.repository;

import hyundai.blog.like.entity.Like;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    // 특정 TIL과 사용자에 대한 좋아요를 조회하는 쿼리 메서드
    Optional<Like> findByMemberAndTil(Member member, Til til);

    // 특정 TIL에 대한 좋아요 개수를 조회하는 쿼리 메서드
    long countByTil(Til til);
}
