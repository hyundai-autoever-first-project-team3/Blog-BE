package hyundai.blog.comment.service;

import hyundai.blog.comment.dto.CommentCreateRequest;
import hyundai.blog.comment.dto.CommentUpdateRequest;
import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment save(CommentCreateRequest request) {
        // 1) dto 를 가지고 entity를 만들어주고
        Comment comment = Comment.builder()
                .memberId(request.getMemberId())
                .tilId(request.getTilId())
                .content(request.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // 2) repository에 entity를 저장해보세요
        Comment savedComment = commentRepository.save(comment);

        log.info("-------------- {} --------------", savedComment);

        return savedComment;
    }

    public Comment update(Long id, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        comment.changeContent(request.getContent());
        comment.changeUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
