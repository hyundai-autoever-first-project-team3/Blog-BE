package hyundai.blog.til.service;

import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.like.repository.LikeRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.dto.TilGetResponse;
import hyundai.blog.til.dto.TilUpdateRequest;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
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
    private final CommentRepository commentRepository;
    private final TilRepository tilRepository;
    private final LikeRepository likeRepository;

    public Til save(TilCreateRequest request) {
        Til til = Til.builder()
                .memberId(request.getMemberId())
                .language(request.getLanguage())
                .site(request.getSite())
                .algorithm(request.getAlgorithm())
                .title(request.getTitle())
                .tag(request.getTag())
                .link(request.getLink())
                .codeContent(request.getCodeContent())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Til savedTil = tilRepository.save(til);

        log.info("--------{}--------");

        return savedTil;
    }

    public Til update(Long id, TilUpdateRequest request) {
        Til til = tilRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        til.change(request);

        Til updatedTil = tilRepository.save(til);

        return updatedTil;
    }

    public void delete(Long id) {
        tilRepository.deleteById(id);
    }

    @Transactional
    public TilGetResponse get(Long id) {
        // tilID로 til 객체 가져오기
        
        Til til = tilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        
        // tilId에 해당하는 comment들 list로 가져오기
        List<Comment> comments = commentRepository.findAllByTilId(id);

        // tilId에 해당하는 like의 개수를 count 하고 변수에 저장
        Long countLikes = likeRepository.countByTilId(id);

        // memberId와 tilId에 해당하는 like가 존재하면 isLiked를 true로 설정
        // 존재하지 않으면 false로 설정

        boolean isLiked;

        // 로그인 된 사용자 정보에서 memberId를 가져오기
        Long memberId = 2L;

        //로그인 된 내 memberId와 해당 tilId가 LikeRepository에 존재하면 isLiked는 true 없으면 false
        if(likeRepository.findByMemberIdAndTilId(memberId, id).isPresent()) {
            isLiked = true;
        } else {
            isLiked = false;
        }

        // til + comment(list) + countLikes + isLiked 를 합쳐서 dto 만들기
        TilGetResponse tilGetResponse = new TilGetResponse(til, comments, countLikes, isLiked);

        // return dto... 하기!
        return tilGetResponse;
    }
}
