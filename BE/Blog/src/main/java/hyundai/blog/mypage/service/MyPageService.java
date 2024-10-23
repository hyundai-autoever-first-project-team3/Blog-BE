package hyundai.blog.mypage.service;

import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.like.entity.Like;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.mypage.dto.StatisticViewDto;
import hyundai.blog.til.dto.TilPreviewDto;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
import hyundai.blog.util.MemberResolver;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private static final int SIZE = 10;

    private final MemberResolver memberResolver;
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public Page<TilPreviewDto> getTils(int page) {
        // 1) Member 정보 조회
        Member member = memberResolver.getCurrentMember();

        // 2) pageable 생성
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "createdAt"));

        // 3) Member id에 해당하는 tils 조회
        Page<Til> tilPage = tilRepository.findAllByMemberId(member.getId(), pageable);

        // 4) TilPreviewDto list 생성
        List<TilPreviewDto> tilPreviewDtos = tilPage.stream()
                .map(til -> {
                    // 1) Like 개수 가져오기
                    Long likeCount = likeRepository.countByTilId(til.getId());

                    // 2) Comment 개수 가져오기
                    Long commentCount = commentRepository.countByTilId(til.getId());

                    // 1, 2, 3을 사용하여 TilPreviewDto 생성
                    return TilPreviewDto.of(til, member, commentCount, likeCount);
                })
                .toList();

        // 5) TilPreviewDto 리스트를 Page<TilPreviewDto>로 변환
        return new PageImpl<>(tilPreviewDtos, pageable, tilPage.getTotalElements());
    }

    public Page<TilPreviewDto> getLikedTils(int page) {
        // 1) Member 정보 조회
        Member member = memberResolver.getCurrentMember();

        // 2) like pageable 생성
        Pageable pageable = PageRequest.of(page, SIZE, Sort.by(Sort.Direction.DESC, "id"));

        // 3) like에 해당하는 객체들 가져오기
        Page<Like> likedTilsPage = likeRepository.findByMember(member, pageable);

        List<TilPreviewDto> tilPreviewDtos = likedTilsPage.stream()
                .map(like -> {
                    Til til = like.getTil();

                    Long likeCount = likeRepository.countByTilId(til.getId());

                    Long commentCount = commentRepository.countByTilId(til.getId());

                    return TilPreviewDto.of(til, member, commentCount, likeCount);
                })
                .toList();

        return new PageImpl<>(tilPreviewDtos, pageable, likedTilsPage.getTotalElements());
    }

    public StatisticViewDto getStatistics() {
        Member loggedInMember = memberResolver.getCurrentMember();

        Long tilsCount = tilRepository.countByMemberId(loggedInMember.getId());

        Long tilsMonthCount = tilRepository.countByMemberIdAndCreatedAtThisMonth(loggedInMember.getId());


        //로그인된 멤버가 작성한 모든 tilId를 모두 조회해서
        List<Til> tils = tilRepository.findAllByMemberId(loggedInMember.getId());

        //likeRepository에서 위의 모든 tilId에 해당하는 것들을 count

        // 각 Til의 like 개수 합산
        Long receivedLikeCount = 0L;

        for (Til til : tils) {
            // 각 Til의 id로 like 개수 조회
            receivedLikeCount += likeRepository.countByTilId(til.getId());
        }

        StatisticViewDto statisticViewDto = StatisticViewDto.of(tilsCount, tilsMonthCount, receivedLikeCount);

        return statisticViewDto;
    }
}
