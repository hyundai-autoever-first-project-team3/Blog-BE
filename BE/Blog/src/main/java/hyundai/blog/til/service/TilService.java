package hyundai.blog.til.service;

import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.til.dto.TilCreateRequest;
import hyundai.blog.til.dto.TilGetResponse;
import hyundai.blog.til.dto.TilUpdateRequest;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.repository.TilRepository;
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

    public TilGetResponse get(Long id) {
        // 1) tilID로 til 객체 가져오기
        
        Til til = tilRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        
        // 2) tilId에 해당하는 comment들 list로 가져오기
        List<Comment> comments = commentRepository.findAllByTilId(id);

        // 3) til + comment(list) 를 합쳐서 dto 만들기
        TilGetResponse tilGetResponse = new TilGetResponse(til, comments);

        // 4) return dto... 하기!
        return tilGetResponse;
    }
}
