package hyundai.blog.til.service;

import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.dto.TilDeleteResponse;
import hyundai.blog.til.dto.TilGetResponse;
import hyundai.blog.til.dto.TilUpdateRequest;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.exception.InvalidTilOwnerException;
import hyundai.blog.til.exception.TilIdNotFoundException;
import hyundai.blog.til.repository.TilRepository;
import hyundai.blog.util.MemberResolver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class TilService {
    private final MemberResolver memberResolver;
    private final CommentRepository commentRepository;
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;

    @Transactional
    public Til save(TilCreateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) til create request & member 로 til entity 생성
        Til til = Til.builder()
                .memberId(loggedInMember.getId())   // 로그인한 멤버의 memberId로 설정
                .language(request.getLanguage())
                .site(request.getSite())
                .algorithm(request.getAlgorithm())
                .title(request.getTitle())
                .tag(request.getTag())
                .link(request.getLink())
                .codeContent(request.getCodeContent())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())     // 현재 시간으로 createdAt 설정
                .updatedAt(LocalDateTime.now())     // 현재 시간으로 updatedAt 설정
                .build();

        // 3) til 엔티티 저장
        Til savedTil = tilRepository.save(til);

        log.info("til 게시글 생성 성공 : {}", savedTil.toString()); // 정상 생성 확인을 위한 log 출력

        // 4) 저장된 엔티티 반환
        return savedTil;
    }

    @Transactional
    public Til update(Long tilId, TilUpdateRequest request) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) tilId에 해당하는 til 엔티티를 가져온다.
        Til til = tilRepository.findById(tilId).orElseThrow(TilIdNotFoundException::new);

        // 3) til 작성자와 로그인한 사용자에 대한 검증
        validateTilOwnerShip(til, loggedInMember);

        // 4) til 수정 작업 수행
        til.change(request);

        // 5) til 수정사항 반영
        Til updatedTil = tilRepository.save(til);

        log.info("til 게시글 수정 성공 : {}", updatedTil.toString()); // 정상 생성 확인을 위한 log 출력

        return updatedTil;
    }

    @Transactional
    public TilDeleteResponse delete(Long tilId) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) tilId에 해당하는 til 엔티티를 가져온다.
        Til til = tilRepository.findById(tilId).orElseThrow(TilIdNotFoundException::new);

        // 3) til 작성자와 로그인한 사용자에 대한 검증
        validateTilOwnerShip(til, loggedInMember);

        // 4) til entity를 db에서 삭제
        tilRepository.delete(til);

        // 5) 로그 메세지 출렵 및 리턴 값을 위한 String
        String message = "til 게시글 삭제 성공";
        log.info(message);

        return TilDeleteResponse.of(til.getId(), message);
    }

    @Transactional
    public TilGetResponse get(Long tilId) {
        // 1) 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 2) tilId에 해당하는 til 엔티티를 가져온다.
        Til til = tilRepository.findById(tilId).orElseThrow(TilIdNotFoundException::new);

        // 3) tilId에 해당하는 comment들 list로 가져오기
        List<Comment> comments = commentRepository.findAllByTilId(tilId);

        // 4) tilId에 해당하는 like의 개수를 count 하고 변수에 저장
        Long countLikes = likeRepository.countByTilId(tilId);

        /*
        5) isLiked 값 설정
        memberId와 tilId에 해당하는 like가 존재하면 isLiked를 true로 설정
        존재하지 않으면 false로 설정

        로그인 된 내 memberId와 해당 tilId가 LikeRepository에 존재하면 isLiked는 true 없으면 false
        */
        boolean isLiked = likeRepository.existsByMemberIdAndTilId(loggedInMember.getId(), tilId);

        // 6) 각각의 entity 및 dto를 바탕으로 getResponse Dto 생성
        // til + comment(list) + countLikes + isLiked 를 합쳐서 dto 만들기
        TilGetResponse tilGetResponse = new TilGetResponse(til, comments, countLikes, isLiked);

        // return dto... 하기!
        return tilGetResponse;
    }

    private void validateTilOwnerShip(Til til, Member loggedInMember) {
        if (!til.getMemberId().equals(loggedInMember.getId())) {
            throw new InvalidTilOwnerException();
        }
    }
}
