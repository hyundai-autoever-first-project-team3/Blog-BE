package hyundai.blog.like.service;

import hyundai.blog.like.dto.LikeCreateRequest;
import hyundai.blog.like.dto.LikeCreateResponse;
import hyundai.blog.like.dto.LikeDeleteResponse;
import hyundai.blog.like.entity.Like;
import hyundai.blog.like.exception.LikeDuplicateException;
import hyundai.blog.like.exception.MemberAndTilNotFoundException;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.exception.MemberIdNotFoundException;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.exception.TilIdNotFoundException;
import hyundai.blog.til.repository.TilRepository;
import hyundai.blog.util.MemberResolver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final TilRepository tilRepository;
    private final MemberResolver memberResolver;

    @Transactional
    public LikeCreateResponse save(LikeCreateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) request의 tilID 가져오고
        Til til = tilRepository.findById(request.getTilId())
                .orElseThrow(TilIdNotFoundException::new);

        if (likeRepository.existsByMemberIdAndTilId(loggedInMember.getId(), til.getId())) {
            throw new LikeDuplicateException();
        }

        // 3) Like 엔티티 만들고
        Like like = Like.builder()
                .member(loggedInMember)
                .til(til)
                .build();

        // 4) Like 엔티티 저장
        likeRepository.save(like);

        Long countLikes = likeRepository.countByTilId(til.getId());

        LikeCreateResponse response = LikeCreateResponse.builder()
                .likeCounts(countLikes)
                .build();

        return response;
    }

    @Transactional
    public LikeDeleteResponse delete(LikeCreateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) tilRepo에서 requestTilId에 해당하는 til 조회
        Til til = tilRepository.findById(request.getTilId())
                .orElseThrow(TilIdNotFoundException::new);


        Like like = likeRepository.findByMemberAndTil(loggedInMember, til).orElseThrow(MemberAndTilNotFoundException::new);

        likeRepository.delete(like);

        Long countLikes = likeRepository.countByTilId(til.getId());

        LikeDeleteResponse response = LikeDeleteResponse.builder()
                .likeCounts(countLikes)
                .build();

        return response;
    }
}
