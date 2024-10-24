package hyundai.blog.comment.service;

import hyundai.blog.comment.dto.CommentCreateRequest;
import hyundai.blog.comment.dto.CommentUpdateRequest;
import hyundai.blog.comment.entity.Comment;
import hyundai.blog.comment.exception.CommentIdNotFoundException;
import hyundai.blog.comment.exception.InvalidCommentOwnerException;
import hyundai.blog.comment.repository.CommentRepository;
import hyundai.blog.member.entity.Member;
import hyundai.blog.til.entity.Til;
import hyundai.blog.til.exception.InvalidTilOwnerException;
import hyundai.blog.util.MemberResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Log4j2
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberResolver memberResolver;


    public Comment save(CommentCreateRequest request) {
        // 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        // 1) dto 를 가지고 entity를 만들어주고
        Comment comment = Comment.builder()
                .memberId(loggedInMember.getId())
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
        // 현재 로그인 된 멤버의 ID를 가져온다.
        Member loggedInMember = memberResolver.getCurrentMember();

        Comment comment = commentRepository.findById(id).orElseThrow(CommentIdNotFoundException::new);

        // 3) til 작성자와 로그인한 사용자에 대한 검증
        validateCommentOwnerShip(comment, loggedInMember);

        comment.changeContent(request.getContent());
        comment.changeUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);

        return savedComment;
    }

    public void delete(Long id) {

        Member loggedInMember = memberResolver.getCurrentMember();

        Comment comment = commentRepository.findById(id).orElseThrow(CommentIdNotFoundException::new);

        validateCommentOwnerShip(comment, loggedInMember);

        commentRepository.deleteById(id);
    }

    private void validateCommentOwnerShip(Comment comment, Member loggedInMember) {
        if (!comment.getMemberId().equals(loggedInMember.getId())) {
            throw new InvalidCommentOwnerException();
        }
    }
}
