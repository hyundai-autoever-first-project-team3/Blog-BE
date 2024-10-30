package hyundai.blog.like.repository;

import hyundai.blog.like.entity.Like;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // 특정 TIL과 사용자에 대한 좋아요를 조회하는 쿼리 메서드
    Optional<Like> findByMemberAndTil(Member member, Til til);

    // 특정 TIL과 사용자에 대한 좋아요 여부를 조회하는 쿼리 메서드
    Optional<Like> findByMemberIdAndTilId(Long memberId, Long tilId);

    Boolean existsByMemberIdAndTilId(Long memberId, Long tilId);

    // tilId에 해당하는 Like의 개수를 카운트하는 메서드
    Long countByTilId(Long tilId);

    // 특정 회원의 좋아요한 TIL 리스트를 ID 내림차순으로 조회
    Page<Like> findByMember(Member member, Pageable pageable);

    void deleteAllByTil(Til til);

}
