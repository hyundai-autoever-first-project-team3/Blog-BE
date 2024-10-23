package hyundai.blog.like.service;

import hyundai.blog.like.dto.LikeCreateRequest;
import hyundai.blog.like.dto.LikeCreateResponse;
import hyundai.blog.like.entity.Like;
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
    public void save(LikeCreateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        Til til = tilRepository.findById(request.getTilId())
                .orElseThrow(TilIdNotFoundException::new);

        Like like = Like.builder()
                .member(loggedInMember)
                .til(til)
                .build();

        likeRepository.save(like);

    }

    @Transactional
    public void delete(LikeCreateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        Til til = tilRepository.findById(request.getTilId())
                .orElseThrow(TilIdNotFoundException::new);

        Like like = likeRepository.findByMemberAndTil(loggedInMember, til).orElseThrow(MemberIdNotFoundException::new);

        likeRepository.delete(like);
    }
}
