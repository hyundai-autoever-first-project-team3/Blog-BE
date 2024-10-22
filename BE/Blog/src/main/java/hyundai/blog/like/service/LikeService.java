package hyundai.blog.like.service;

import hyundai.blog.like.dto.LikeCreateRequest;
import hyundai.blog.like.dto.LikeCreateResponse;
import hyundai.blog.like.entity.Like;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.member.repository.MemberRepository;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final TilRepository tilRepository;

    public void save(LikeCreateRequest request) {
        Optional<Member> member = memberRepository.findById(request.getMemberId());
        Optional<Til> til = tilRepository.findById(request.getTilId());

        Like like = Like.builder()
                .member(member.get())
                .til(til.get())
                .build();

        likeRepository.save(like);

    }
}
